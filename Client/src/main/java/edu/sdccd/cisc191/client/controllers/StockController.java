package edu.sdccd.cisc191.client.controllers;

import edu.sdccd.cisc191.client.models.DefaultStocksFileIO;
import edu.sdccd.cisc191.client.models.RankedResult;
import edu.sdccd.cisc191.common.entities.Stock;
import edu.sdccd.cisc191.common.entities.DataFetcher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import edu.sdccd.cisc191.common.entities.StockList;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

import java.util.concurrent.*;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * StockController*
 * Handles the views for a user's dashboard of stock data
 * as well as the interactivity with the data.
 */
@Controller
public class StockController implements DataFetcher {

    public StockList stockList = new StockList(); //List of stocks the user follows.
    RestTemplate restTemplate = new RestTemplate();
    String resourceURL = DataFetcher.backendEndpointURL + DataFetcher.apiEndpointURL;

    /**
     * Sets up to display the dashboard, which displays a list of stocks the user follows.
     * @param model model to display stocks
     * @return dashboard the dashboard page
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        //Temporary file i/o for reading a list of stocks
        //until we have proper authentication working.
        //Will be replaced by the user's followed stocks instead of
        //default.
        DefaultStocksFileIO defaultStocks = new DefaultStocksFileIO();
        defaultStocks.readAndUpdateDefaultStocks();
        ArrayList<String> tickers = defaultStocks.getDefaultStocks();

        System.out.println(defaultStocks.getDefaultStocks());
        //For use in html
        LinkedList<Stock> stocks = null;

        for (String ticker : tickers) {
            Stock stock;
            try {
                ResponseEntity<Stock> response = restTemplate.exchange(
                        resourceURL + "/stock/" + ticker,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                stock = response.getBody();
                if(stock != null) {
                    this.stockList.add(stock);
                    System.out.println("Added stock");
                }
                else {
                    System.out.println("No stock found.");
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        if(this.stockList.length() != 0) {
            stocks = this.stockList.getStocks();
        }

        model.addAttribute("stocks", stocks);

        return "dashboard";
    }

    /**
     * Sets up to display the stock page
     * @param ticker the Long id that identifies each stock
     * @param model the method to create the stock listing
     * @return stock the stock page
     */
    @GetMapping("/dashboard/stock/{ticker}")
    public String stockDetails(@PathVariable("ticker") String ticker, Model model) {
        //Stock variable for passing into template
        Stock stock;

        try {
            ResponseEntity<Stock> response = restTemplate.exchange(
                    resourceURL + "/stock/" + ticker,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            stock = response.getBody();
        } catch (Exception e) {
            stock = null;
        }

        model.addAttribute("stock", stock);
        return "stock";
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("query", "...");
        model.addAttribute("stocks", new ArrayList<String>());
        return "search";
    }

    @GetMapping("/search/{query}")
    public String searchWithQuery(@PathVariable("query") String query, Model model) {
        System.out.println(allTickers.size());
        List<ExtractedResult> topResults = FuzzySearch.extractTop(query, allTickers, 10);

        ArrayList<RankedResult> rankedResultList = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(topResults.size());
        CompletionService<RankedResult> completionService = new ExecutorCompletionService<>(executor);

        int totalStockOptions = 0;

        for(ExtractedResult result : topResults) {
            if(result.getScore() < 50) {
                break;
            }

            totalStockOptions += 1;

            completionService.submit(() -> {
                try {
                    String ticker = result.getString();
                    System.out.println(result.getScore());
                    ResponseEntity<Stock> response = restTemplate.exchange(
                            resourceURL + "/stock/" + ticker,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<>() {}
                    );
                    RankedResult rankedResult = new RankedResult(result.getScore(), response.getBody());
                    return rankedResult;
                } catch (Exception e) {
                    System.err.println(e);
                    return null;
                }
            });
        }

        int received = 0;
        while(received < totalStockOptions) {
            try {
                RankedResult rankedResult = completionService.take().get();
                if(rankedResult.getStock().getName() != null) {
                    rankedResultList.add(rankedResult);
                }
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }

            received++;
        }

        rankedResultList.sort(Comparator.comparing(RankedResult::getScore).reversed());

        // extract stock from rankedResult from rankedResultList
        List<Stock> stockList = rankedResultList.stream().map(RankedResult::getStock).collect(Collectors.toList());

        // shutdown threads
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error shutting down threads: " + e);
        }

        model.addAttribute("query", query);
        model.addAttribute("stocks", stockList);
        return "search";
    }
}
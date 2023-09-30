package edu.sdccd.cisc191.client;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sdccd.cisc191.client.models.Requests;

import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Client {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Client.class, args);

        // try {
        //     String result = Requests.get("https://example.com");
        //     System.out.println(result);

        // } catch(MalformedURLException e) {
        //     System.err.println(e);
        // }

        /*
        // Test a get request
        String testTicker = "AAPL";
        String testOutput = ClientController.getRequest("https://finnhub.io/api/v1/stock/candle?symbol="+testTicker+"&resolution=60&from=1693493346&to=1693752546&token=bsq5ig8fkcbcavsjbrrg");
        //System.out.println(testOutput);
        StockCandle candles = new StockCandle(testTicker,testOutput);
        double[][] data = candles.getStockInfo();
        System.out.println(candles.toString());
        candles.printConciseContents();
            }
        }*/

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
/*            Stock[] stocks = restTemplate.getForObject(
                    "http://localhost:8080/stocks", Stock[].class);

            for(Stock stock : stocks) {
                System.out.println(stock.getName());
            }*/
            System.out.println("Listening on port 8080.");
        };
    }
}
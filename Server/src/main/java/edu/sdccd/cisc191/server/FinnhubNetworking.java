package edu.sdccd.cisc191.server;

//import edu.sdccd.cisc191.common.entities.Stock;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sdccd.cisc191.common.entities.DataFetcher;
import edu.sdccd.cisc191.common.entities.Requests;

import java.net.MalformedURLException;

/*
This class contains 3 static methods for conducting API calls to FinnHub
to generate stocks and stock candles on the server.
 */
public class FinnhubNetworking {

    private static final String token = DataFetcher.finnhubKey;

    public static long secondsBeforeRefreshNeeded = 60; // number of seconds before a cached stock will be forced to refresh

    private static int callsSinceRefresh = 0;
    private static long lastUpdateTime = 0;

    // Get data from a provided URL, in String format
    public static String fetchData(String url) {
        try {
            callsSinceRefresh++;
            long timeDifference = TimeMethods.now() - lastUpdateTime;
            if (callsSinceRefresh >= 30 && timeDifference <= 1000L) {
                Thread.sleep(1000L - timeDifference);
                callsSinceRefresh = 0;
            }
            lastUpdateTime = TimeMethods.now();
            String result = Requests.get(url);
            return result;

        } catch (InterruptedException e) {
            System.out.println("Critical error encountered with API concurrency.");
            return "ERROR";
        } catch (MalformedURLException e) {
            return "ERROR";
        }
    }

    // Request JSON for a Stock (not a Candle) from FinnHub
    public static String getJsonFromFinnhub(String ticker) throws MalformedURLException, JsonProcessingException {
        String companyInfoJson = fetchData("https://finnhub.io/api/v1/stock/profile2?symbol="
                    + ticker + "&token=" + token);
        String stockPriceJson = fetchData("https://finnhub.io/api/v1/quote?symbol="
                    + ticker + "&token=" + token);
        JsonObject obj1 = new JsonObject(companyInfoJson);
        JsonObject obj2 = new JsonObject(stockPriceJson);
        return obj1.mergeStockData(obj2).getString();
    }

    // Get StockCandle data from FinnHub
    public static String getCandleFromFinnhub(String ticker, String duration, long time1, long time2) throws MalformedURLException, JsonProcessingException {
        String resolution = TimeMethods.getFrequency(duration);
        long[] time = TimeMethods.getTimeRange(duration);
        String URL = "https://finnhub.io/api/v1/stock/candle?symbol=" + ticker
                + "&resolution=" + resolution
                + "&from=" + time[0] + "&to=" + time[1]
                + "&token=" + DataFetcher.finnhubKey;
        String result = fetchData(URL);
        JsonObject output = new JsonObject(result);
        output.annotateForCandles(ticker,duration,time1,time2);
        return output.getString();
    }
}

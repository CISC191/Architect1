package edu.sdccd.cisc191.server;

//import edu.sdccd.cisc191.common.entities.Stock;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sdccd.cisc191.common.entities.DataFetcher;
import edu.sdccd.cisc191.common.entities.Requests;

import java.net.MalformedURLException;

public class FinnhubNetworking {

    private static final String token = DataFetcher.finnhubKey;

    public static long secondsBeforeRefreshNeeded = 60; // number of seconds before a cached stock will be forced to refresh

    public static String getJsonFromFinnhub(String ticker) throws MalformedURLException, JsonProcessingException {
        String companyInfoJson = Requests.get("https://finnhub.io/api/v1/stock/profile2?symbol="
                + ticker + "&token=" + token);
        String stockPriceJson = Requests.get("https://finnhub.io/api/v1/quote?symbol="
                + ticker + "&token=" + token);
        return DataMethods.mergeStockJson(companyInfoJson,stockPriceJson);
    }

    // The static list of index attributes we want to request from FinnHub API
    public static String getCandleFromFinnhub(String ticker, String duration, long time1, long time2) throws MalformedURLException, JsonProcessingException {
        String resolution = TimeMethods.getFrequency(duration);
        String URL = "https://finnhub.io/api/v1/stock/candle?symbol="+ticker
                +"&resolution="+resolution
                +"&from="+time1+"&to="+time2
                +"&token="+DataFetcher.finnhubKey;
        return DataMethods.annotateCandles(Requests.get(URL),ticker,duration,time1,time2);
    }
}
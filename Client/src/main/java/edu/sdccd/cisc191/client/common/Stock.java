package edu.sdccd.cisc191.client.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sdccd.cisc191.common.entities.Requests;

import java.net.MalformedURLException;
//import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    //@JsonProperty("id")
    private Long id;

    private String ticker;
    private String name;
    private String description;
    private double sharePrice;
    private double dividendYield;
    private String stockSector;

    public static long lastId = 0L;

    public Stock () {

    }
    public Stock(long id, String ticker, String newName, String description,
                 String sector, double price, double dividend){
        id = ++lastId;
        this.id = id;
        this.ticker = ticker;
        this.name = newName;
        this.description = description;
        this.sharePrice = price;
        this.dividendYield = dividend;
        this.stockSector = sector;
    }

    public Stock(String newTicker) throws MalformedURLException, JsonProcessingException {
        // This constructor takes a Ticker and gets all the relevant info
        id = ++lastId;
        // Get the JSON data with basic info about the stock, such as company name/description
        String jsonInput = Requests.get("https://finnhub.io/api/v1/stock/profile2?symbol="+newTicker+"&token="+Requests.token);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonInput);

        // Update core attributes accordingly
        ticker = newTicker;
        name = rootNode.get("name").asText();
        description = "A company called "+name;
        stockSector = rootNode.get("finnhubIndustry").asText();

        // Get the JSON data with FINANCIAL info such as price
        String jsonInput2 = Requests.get("https://finnhub.io/api/v1/quote?symbol="+newTicker+"&token="+Requests.token);
        ObjectMapper mapper2 = new ObjectMapper();
        JsonNode rootNode2 = mapper2.readTree(jsonInput2);

        // Update core attributes accordingly
        ticker = newTicker;
        sharePrice = rootNode2.get("c").asDouble();
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return sharePrice;
    }

    public void setPrice(double price) {
        this.sharePrice = price;
    }

    public double getDividend() {
        return dividendYield;
    }

    public void setDividend(double dividend) {
        this.dividendYield = dividend;
    }

    public String getSector() {
        return stockSector;
    }

    public void setSector(String name) {
        this.stockSector = name;
    }

    public Long getId() {
        return this.id;
    }

    public String toString() {
        return String.format("[%s] %s. Sect: %s. Desc: %s. $%.2f/share.",
                ticker,name,stockSector,description,sharePrice);
    }
}

package edu.sdccd.cisc191.common.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * a program that creates a graph for a selected stock
 */

public class StockCandle {

    /*

    FORMAT FOR STOCK CANDLE DATA:

    StockCandle.getStockInfo(): double[][]
    Is an array of sub-arrays, each corresponding to a certain instant in time.

    Each sub-array is indexed by (all doubles):

    0. Closing cost
    1. High cost
    2. Low cost
    3. Open cost
    4. Time stamp

     */

    protected long id;

    protected String ticker;

    protected long lastRefreshTime;

    protected String duration;

    protected long time1; // Start time (ms)

    protected long time2; // End time (ms)

    //protected double[][] stockInfo;

    protected List<Double> c; // Closing prices
    protected List<Double> h; // High prices
    protected List<Double> l; // Low prices
    protected List<Double> o; // Open prices
    protected List<Long> t; // Time stamps
    protected List<Long> v; // Trade volume

    public List<Double> getC() { return c; }
    public List<Double> getH() { return h; }
    public List<Double> getL() { return l; }
    public List<Double> getO() { return o; }
    public List<Long> getT() { return t; }
    public List<Long> getV() { return v; }

    public void setC(List<Double> val) { c = val; }
    public void setH(List<Double> val) { h = val; }
    public void setL(List<Double> val) { l = val; }
    public void setO(List<Double> val) { o = val; }
    public void setT(List<Long> val) { t = val; }
    public void setV(List<Long> val) { v = val; }

    public void setTicker(String txt){
        ticker = txt;
    }

    public String getTicker(){
        return ticker;
    }

    public void setDuration(String res){
        duration = res;
    }

    public String getDuration(){
        return duration;
    }

    public void setTime1(long res){
        time1 = res;
    }

    public long getTime1(){
        return time1;
    }

    public void setTime2(long res){
        time2 = res;
    }

    public long getTime2(){
        return time2;
    }

    public long getId() {return id;}

    public void setId(long val) { id = val; }

    public void setLastRefresh(long stamp) { lastRefreshTime = stamp; }

    public long getLastRefresh() { return lastRefreshTime; }

    public StockCandle(String newTicker, List<List<?>> values){
        setTicker(newTicker);
        c = (List<Double>)values.get(0);
        h = (List<Double>)values.get(1);
        l = (List<Double>)values.get(2);
        o = (List<Double>)values.get(3);
        t = (List<Long>)values.get(4);
        v = (List<Long>)values.get(5);
        //setStockInfo(array);
    }

    public StockCandle(){
        // Blank constructor
    }

    public Number[][] getStockInfo(){
        if (c == null) { return null; }
        Number[][] stockInfo = new Number[c.size()][6];
        List<?>[] parse = new List<?>[]{c,h,l,o,t,v};
        for(int i = 0; i < c.size(); i++){
            for(int j = 0; j < 6; j++){
                stockInfo[i][j] = (Number)parse[j].get(i);
            }
        }
        return stockInfo;
    }

    /*public double[][] getStockInfo(){
        return stockInfo;
    }

    public void setStockInfo(double[][] array){
        stockInfo = array;
    }*/

    /*public String toString(){
        String outputString = "";
        for (double[] row : stockInfo){
            outputString += String.format("@t=%.0f, %s low=%.2f, high=%.2f, open=%.2f, close=%.2f.",row[4],ticker,row[2],row[1],row[3],row[0]);
            outputString += "\n";
        }
        return outputString;
    }*/

    /*public void printConciseContents(){
        System.out.println(ticker+":");
        for (double[] row : stockInfo){
            String outputString = "{";
            for (double item : row){
                outputString += item;
                if (item != row[row.length-1]){
                    outputString+=", ";
                }
            }
            outputString+="}";
            if (row != stockInfo[stockInfo.length-1]) {
                outputString += ", ";
            }
            System.out.println(outputString);
        }
    }*/
}

package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

public enum SeriesType {
    OPEN("open"),
    HIGH("high"),
    CLOSE("close"),
    LOW("low");

    private String seriesType;

    SeriesType(String seriesType){
        this.seriesType = seriesType;
    }

    @Override
    public String toString() {
        return seriesType;
    }
}
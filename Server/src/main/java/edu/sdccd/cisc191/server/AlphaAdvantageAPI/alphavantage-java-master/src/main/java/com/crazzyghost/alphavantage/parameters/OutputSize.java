package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

public enum OutputSize {

    COMPACT("compact"),
    FULL("full");

    private String outputSize;

    OutputSize(String outputSize){
        this.outputSize = outputSize;
    }

    @Override
    public String toString() {
        return this.outputSize;
    }
}

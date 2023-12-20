package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

public enum DataType {

    JSON("json"),
    CSV("csv");

    private String dataType;

    DataType(String dataType){
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return dataType;
    }
}

package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

public class SimpleIndicatorRequest extends IndicatorRequest {

    private SimpleIndicatorRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends IndicatorRequest.Builder<Builder>{

        @Override
        public IndicatorRequest build() {
            return new SimpleIndicatorRequest(this);
        }

    }
}
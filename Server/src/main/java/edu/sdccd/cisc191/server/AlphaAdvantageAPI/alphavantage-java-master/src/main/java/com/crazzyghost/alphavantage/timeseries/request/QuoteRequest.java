package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

import com.crazzyghost.alphavantage.parameters.Function;

public class QuoteRequest extends TimeSeriesRequest {

    private QuoteRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends TimeSeriesRequest.Builder<Builder>{
        public Builder(){
            this.function(Function.GLOBAL_QUOTE);
        }

        @Override
        public QuoteRequest build(){
            return new QuoteRequest(this);
        }
    }


    
}
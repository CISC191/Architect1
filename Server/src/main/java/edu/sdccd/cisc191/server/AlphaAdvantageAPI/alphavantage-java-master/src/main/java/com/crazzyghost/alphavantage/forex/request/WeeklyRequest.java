package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

import com.crazzyghost.alphavantage.parameters.Function;

public class WeeklyRequest extends ForexRequest{

    private Function function;

    private WeeklyRequest(Builder builder){
        super(builder);
        this.function = Function.FX_WEEKLY;
    }


    public static class Builder extends ForexRequest.Builder<Builder> {

        public Builder(){
            super();
        }

        @Override
        public WeeklyRequest build() {
            return new WeeklyRequest(this);
        }
    }
}

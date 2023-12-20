package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

import com.crazzyghost.alphavantage.parameters.Function;

/**
 * @author crazzyghost
 * @since 1.4.0
 * A Sector request 
 */
public final class SectorRequest {

    private Function function;

    private SectorRequest(Builder builder){
        this.function = builder.function;
    }

    public static class Builder {

        private Function function;

        public Builder(){
            this.function = Function.SECTOR;
        }

        public SectorRequest build(){
            return new SectorRequest(this);
        }

    }
}
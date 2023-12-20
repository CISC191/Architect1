/*
 *
 * Copyright (c) 2020 Sylvester Sefa-Yeboah
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

/**
 * Crypto Currency Request
 *
 * @author Sylvester Sefa-Yeboah
 * @since 1.0.0
 */
public class DigitalCurrencyRequest extends CryptoRequest {

    private final String market;

    private DigitalCurrencyRequest(Builder builder) {
        super(builder);
        this.market = builder.market;
    }


    public static class Builder extends CryptoRequest.Builder<Builder> {

        private String market;
        
        public String getMarket() {
            return market;
        }

        public Builder() { }

        public Builder market(String market) {
            this.market = market;
            return this;
        }

        @Override
        public CryptoRequest build(){
            return new DigitalCurrencyRequest(this);
        }
    }
    
}
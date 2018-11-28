package com.tradingPlatform.DataObjects;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CoinInfo implements Serializable {

    private int id;
    private String name;
    private String symbol;
    private int circulatingSupply;
    private String dateAdded;
    private int cmcRank;
    private String currency;
    private double price;
    private double percentChange24hr;

    private CoinInfo(Builder builder) {
        id = builder.id;
        name = builder.name;
        symbol = builder.symbol;
        circulatingSupply = builder.circulatingSupply;
        dateAdded = builder.dateAdded;
        cmcRank = builder.cmcRank;
        currency = builder.currency;
        price = builder.price;
        percentChange24hr = builder.percentChange24hr;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getCirculatingSupply() {
        return circulatingSupply;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public int getCmcRank() {
        return cmcRank;
    }

    public String getCurrency() {
        return currency;
    }

    public double getPrice() {
        return price;
    }

    public double getPercentChange24hr() {
        return percentChange24hr;
    }

    public static final class Builder {
        private int id;
        private String name;
        private String symbol;
        private int circulatingSupply;
        private String dateAdded;
        private int cmcRank;
        private String currency;
        private double price;
        private double percentChange24hr;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder symbol(String val) {
            symbol = val;
            return this;
        }

        public Builder circulatingSupply(int val) {
            circulatingSupply = val;
            return this;
        }

        public Builder dateAdded(String val) {
            dateAdded = val;
            return this;
        }

        public Builder cmcRank(int val) {
            cmcRank = val;
            return this;
        }

        public Builder currency(String val) {
            currency = val;
            return this;
        }

        public Builder price(double val) {
            price = val;
            return this;
        }

        public Builder percentChange24hr(double val) {
            percentChange24hr = val;
            return this;
        }

        public CoinInfo build() {
            return new CoinInfo(this);
        }
    }
    public String toString() {
        return id+"\n"+name+"\n"+symbol+"\n"+circulatingSupply+"\n"+dateAdded+"\n"+cmcRank+"\n"+currency+"\n"+price+"\n"+percentChange24hr;
    }
}

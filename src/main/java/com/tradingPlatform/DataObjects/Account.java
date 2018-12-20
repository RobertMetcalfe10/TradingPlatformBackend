package com.tradingPlatform.DataObjects;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {

    private HashMap<String, Double> currentBalance;
    private ArrayList<Transaction> recentTransactions;

    @PersistenceConstructor
    public Account() { }

    private Account(Builder builder) {
        currentBalance = builder.currentBalance;
        recentTransactions = builder.recentTransactions;
    }

    public HashMap<String, Double> getCurrentBalance() {
        return currentBalance;
    }

    public ArrayList<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void addTransaction(Transaction transaction){
        recentTransactions.add(transaction);
    }

    public static final class Builder {
        private HashMap<String, Double> currentBalance = new HashMap<>();
        private ArrayList<Transaction> recentTransactions = new ArrayList<>();

        public Builder() {
        }

        public Builder initBalance () {
            currentBalance.put("BTC",0.0);
            currentBalance.put("ETH",0.0);
            currentBalance.put("TRX",0.0);
            currentBalance.put("XLM",0.0);
            currentBalance.put("MIOTA",0.0);
            currentBalance.put("XRP",0.0);
            currentBalance.put("BCH",0.0);
            currentBalance.put("LTC",0.0);
            return this;
        }

        public Builder updateCoin(String coinSymbol, double val) {
            currentBalance.put(coinSymbol, val);
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    public String toString() {
        return currentBalance+"";
    }
}

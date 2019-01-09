package com.tradingPlatform.DataObjects;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void changeBalanceForBuyer(double amount) {
        Double balance = currentBalance.get("Dollars");
        balance -= amount;
        currentBalance.put("Dollars",balance);
    }

    public void changeBalanceForSeller(double amount) {
        Double balance = currentBalance.get("Dollars");
        balance += amount;
        currentBalance.put("Dollars",balance);
    }

    public ArrayList<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void addTransaction(Transaction transaction){
        recentTransactions.add(transaction);
    }

    public boolean removeFromBalanceOfCoin(String coinSymbol, double amountCoin){

        for (Map.Entry<String, Double> entry : currentBalance.entrySet()){
            if(entry.getKey().equals(coinSymbol)){
                double amount = entry.getValue()-amountCoin;
                if(amount>=0.0) {
                    currentBalance.replace(entry.getKey(), amount);
                    return true;
                }else{
                    return false;
                }
            }
        }

        return false;
    }

    public boolean addToBalanceOfCoin(String coinSymbol, double amountCoin){

        for (Map.Entry<String, Double> entry : currentBalance.entrySet()){
            if(entry.getKey().equals(coinSymbol)){
                double amount = entry.getValue()+amountCoin;
                currentBalance.replace(entry.getKey(), amount);
                return true;
            }
        }
        return false;
    }

    public static final class Builder {
        private HashMap<String, Double> currentBalance = new HashMap<>();
        private ArrayList<Transaction> recentTransactions = new ArrayList<>();

        public Builder() {
        }

        public Builder initBalance () {
            currentBalance.put("BTC",10.0);
            currentBalance.put("ETH",10.0);
            currentBalance.put("TRX",10.0);
            currentBalance.put("XLM",10.0);
            currentBalance.put("MIOTA",10.0);
            currentBalance.put("XRP",10.0);
            currentBalance.put("BCH",10.0);
            currentBalance.put("LTC",10.0);
            currentBalance.put("Dollars",0.0);
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

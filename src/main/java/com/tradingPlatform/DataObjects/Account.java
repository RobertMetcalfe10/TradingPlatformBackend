package com.tradingPlatform.DataObjects;

import java.util.ArrayList;

public class Account {

    private double currentBalance;
    private ArrayList<Transaction> recentTransactions;

    private Account(Builder builder) {
        currentBalance = builder.currentBalance;
        recentTransactions = builder.recentTransactions;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public ArrayList<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public static final class Builder {
        private double currentBalance;
        private ArrayList<Transaction> recentTransactions;

        public Builder() {
        }

        public Builder currentBalance(double val) {
            currentBalance = val;
            return this;
        }

        public Builder recentTransactions(ArrayList<Transaction> val) {
            recentTransactions = val;
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

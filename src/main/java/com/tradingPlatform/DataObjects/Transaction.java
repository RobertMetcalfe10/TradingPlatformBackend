package com.tradingPlatform.DataObjects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Transaction implements Serializable {

    @Id
    private String id;
    private String seller;
    private String buyer;
    private String coinSymbol;
    private double amountDollar;
    private double amountCoin;

    @PersistenceConstructor
    public Transaction(String id, String seller, String buyer, String coinSymbol, double amountDollar, double amountCoin) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.coinSymbol = coinSymbol;
        this.amountDollar = amountDollar;
        this.amountCoin = amountCoin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public double getAmountDollar() {
        return amountDollar;
    }

    public void setAmountDollar(double amountDollar) {
        this.amountDollar = amountDollar;
    }

    public double getAmountCoin() {
        return amountCoin;
    }

    public void setAmountCoin(double amountCoin) {
        this.amountCoin = amountCoin;
    }
}

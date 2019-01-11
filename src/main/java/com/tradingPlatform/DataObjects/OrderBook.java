package com.tradingPlatform.DataObjects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Document(collection = "OrderBook")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderBook implements Serializable {

    private ArrayList<Transaction> sortedTransactions = new ArrayList<>();
    @Id
    private String id="OrderBook";

    public OrderBook() {

    }

    public void addTransaction(Transaction transaction){
        this.sortedTransactions.add(transaction);
    }

    public void removeTransaction(String id)
    {
        Transaction transaction = null;
        for (Transaction t:this.sortedTransactions) {
            if (t.getId().equals(id)) {
                transaction = t;
            }
        }
        this.sortedTransactions.remove(transaction);
    }

}

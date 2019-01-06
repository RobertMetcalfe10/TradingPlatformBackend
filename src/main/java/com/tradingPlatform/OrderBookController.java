package com.tradingPlatform;

import com.google.gson.*;
import com.tradingPlatform.DataObjects.OrderBook;
import com.tradingPlatform.DataObjects.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderBookController {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "order");
        SpringApplication.run(OrderBookController.class, args);
    }

}

@RestController
@RequestMapping("/orderbook")
class OrderBookRestController {

    private OrderBookRepository orderBookRepository;

    public OrderBookRestController(OrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
        this.orderBookRepository.save(new OrderBook());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/transactions")
    public String getListOfTransactions() {
        List<OrderBook> orderBook = orderBookRepository.findAll();
        Gson gson = new Gson();
        return gson.toJson(orderBook.get(0));
    }

    @GetMapping("/getOrderbook")
    public OrderBook getOrderBook() {
        List<OrderBook> orderBook = orderBookRepository.findAll();
        return orderBook.get(0);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/sell", consumes = "application/json")
    public ResponseEntity sellCoin(@RequestBody String json){
        JsonObject jsonObj;
        String id = "";
        String seller = "";
        String buyer = "";
        String coinSymbol = "";
        double amountDollar = 0.0;
        double amountCoin = 0.0;
        Transaction transaction = null;

        try {
            JsonParser parser = new JsonParser();
            jsonObj = parser.parse(json).getAsJsonObject();
            id = jsonObj.get("id").getAsString();

            seller = jsonObj.get("seller").getAsString();
            buyer = jsonObj.get("buyer").getAsString();
            coinSymbol = jsonObj.get("coinSymbol").getAsString();
            amountDollar = jsonObj.get("amountDollar").getAsDouble();
            amountCoin = jsonObj.get("amountCoin").getAsDouble();

            transaction = new Transaction(id, seller, buyer, coinSymbol, amountDollar, amountCoin);

            OrderBook orderBook = getOrderBook();
            orderBook.addTransaction(transaction);
            orderBookRepository.save(orderBook);


        } catch (JsonIOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.accepted().build();

    }


}



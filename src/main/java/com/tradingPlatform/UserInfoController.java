package com.tradingPlatform;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tradingPlatform.Repositories.CMCRepository;
import com.tradingPlatform.DataObjects.Account;
import com.tradingPlatform.DataObjects.OrderBook;
import com.tradingPlatform.DataObjects.Transaction;
import com.tradingPlatform.DataObjects.UserInfo;
import com.tradingPlatform.Repositories.OrderBookRepository;
import com.tradingPlatform.Repositories.UserInfoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
public class UserInfoController {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "user");
        SpringApplication.run(UserInfoController.class, args);
    }

}

@RestController
@RequestMapping("/user")
class UserInfoRestController {


    private UserInfoRepository userInfoRepository;
    private OrderBookRepository orderBookRepository;
    private CMCRepository cmcRepository;


    public UserInfoRestController (UserInfoRepository userInfoRepository, OrderBookRepository orderBookRepository, CMCRepository cmcRepository) {
        this.userInfoRepository = userInfoRepository;
        this.orderBookRepository = orderBookRepository;
        this.cmcRepository = cmcRepository;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam(value = "userName") String userName) {
        return userInfoRepository.findByUserName(userName).toString();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/balance")
    public String getBalance(@RequestParam Map<String,String> requestParams) {

        String userName = requestParams.get("userName");
        String symbol = requestParams.get("symbol");
        UserInfo userInfo = userInfoRepository.findByUserName(userName);
        if(userInfo!=null) {
            return userInfo.getAccount().getCurrentBalance().get(symbol).toString();
        }else{
            return "";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/totalBalance")
    public String getTotalBalance(@RequestParam(value = "userName") String userName) {
        UserInfo userInfo = userInfoRepository.findByUserName(userName);
        if(userInfo!=null) {
            return Double.toString(userInfo.getAccount().getCurrentBalance().get("Dollars"));
        }else{
            return "";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/buy", consumes = "application/json")
    public ResponseEntity buyCoin(@RequestBody String json){

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

            UserInfo sellerAcc = userInfoRepository.findByUserName(seller);
            UserInfo buyerAcc = userInfoRepository.findByUserName(buyer);

            //if buyers balance is less the price of coin, dont allow them to buy it
            if(buyerAcc.getAccount().getCurrentBalance().get("Dollars") >= amountDollar) {

                //if buyer is the same as seller, remove transaction from order book but keep balances the same
                if (!buyerAcc.getUserName().equals(sellerAcc.getUserName())) {

                    sellerAcc.getAccount().addTransaction(transaction);
                    sellerAcc.getAccount().changeBalanceForSeller(amountDollar);
                    userInfoRepository.save(sellerAcc);

                    buyerAcc.getAccount().addTransaction(transaction);
                    buyerAcc.getAccount().addToBalanceOfCoin(coinSymbol, amountCoin);
                    buyerAcc.getAccount().changeBalanceForBuyer(amountDollar);
                    userInfoRepository.save(buyerAcc);

                }

                if(buyerAcc.getUserName().equals(sellerAcc.getUserName())){
                    buyerAcc.getAccount().addToBalanceOfCoin(coinSymbol, amountCoin);
                    userInfoRepository.save(buyerAcc);
                }

                List<OrderBook> orderBook = orderBookRepository.findAll();
                orderBook.get(0).removeTransaction(id);
                orderBookRepository.save(orderBook.get(0));
            }else{

            }

        } catch (JsonIOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.accepted().build();
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/loginUser")
    public ResponseEntity loginUser(@RequestParam(value = "userName") String userName) {
        UserInfo user = userInfoRepository.findByUserName(userName);
        if(user==null){
            try {
                UserInfo.Builder userInfoBuilder = new UserInfo.Builder();
                userInfoBuilder.userName(userName)
                        .loggedIn(true)
                        .account(new Account.Builder().initBalance().updateCoin("Dollars",10000).build());
                userInfoRepository.save(userInfoBuilder.build());
                return ResponseEntity.accepted().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }else{
            return ResponseEntity.accepted().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/changeBalance")
    public ResponseEntity changeBalance(@RequestParam Map<String,String> requestParams) {

        try {
            String userName = requestParams.get("userName");
            String amount = requestParams.get("amount");
            UserInfo userInfo = userInfoRepository.findByUserName(userName);

            userInfo.getAccount().changeBalanceForBuyer(Double.parseDouble(amount));
            userInfoRepository.save(userInfo);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

}

package com.tradingPlatform;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tradingPlatform.DataObjects.Account;
import com.tradingPlatform.DataObjects.Transaction;
import com.tradingPlatform.DataObjects.UserInfo;
import javafx.collections.transformation.SortedList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    public UserInfoRestController (UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam(value = "userName") String userName) {
        return userInfoRepository.findByUserName(userName).toString();
    }

    @RequestMapping("/balance")
    public String getBalance(@RequestParam Map<String,String> requestParams) {

        String userName = requestParams.get("userName");
        String symbol = requestParams.get("symbol");
        System.out.println(userName);
        System.out.println(symbol);
        UserInfo userInfo = userInfoRepository.findByUserName(userName);
        System.out.println(userInfo.getAccount().getCurrentBalance().get(symbol));

        return userInfo.getAccount().getCurrentBalance().get(symbol).toString();
    }

    @PostMapping(value = "/buy", consumes = "application/json")
    public ResponseEntity.BodyBuilder buyCoin(@RequestBody String json){

        JsonObject jsonObj;
        String seller = "";
        String buyer = "";
        String coinSymbol = "";
        double amountEuro = 0.0;
        double amountCoin = 0.0;
        Transaction transaction = null;

        try {
            JsonParser parser = new JsonParser();
            jsonObj = parser.parse(json).getAsJsonObject();
            seller = jsonObj.get("seller").getAsString();
            buyer = jsonObj.get("buyer").getAsString();
            coinSymbol = jsonObj.get("coinSymbol").getAsString();
            amountEuro = jsonObj.get("amountDollar").getAsDouble();
            amountCoin = jsonObj.get("amountCoin").getAsDouble();

            transaction = new Transaction(seller, buyer, coinSymbol, amountEuro, amountCoin);

            UserInfo sellerAcc = userInfoRepository.findByUserName(seller);
            sellerAcc.getAccount().addTransaction(transaction);

            UserInfo buyerAcc = userInfoRepository.findByUserName(buyer);
            buyerAcc.getAccount().addTransaction(transaction);

        } catch (JsonIOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest();
        }

        return ResponseEntity.accepted();
    }

    @RequestMapping("/createUser")
    public void testUser(@RequestParam(value = "userName") String userName) {
        userInfoRepository.deleteAll();
        UserInfo.Builder userInfoBuilder = new UserInfo.Builder();
        userInfoBuilder.userName(userName)
                .loggedIn(true)
                .account(new Account.Builder().initBalance().updateCoin("BTC",100).build());
        userInfoRepository.save(userInfoBuilder.build());
    }

}

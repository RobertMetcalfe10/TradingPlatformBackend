package com.tradingPlatform;

import com.tradingPlatform.DataObjects.Account;
import com.tradingPlatform.DataObjects.Transaction;
import com.tradingPlatform.DataObjects.UserInfo;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/buy")
    public void buyCoin(@RequestParam(value = "json") String json){

        JSONObject jsonObj;
        String seller = "";
        String buyer = "";
        String coinSymbol = "";
        double amountEuro = 0.0;
        double amountCoin = 0.0;
        Transaction transaction = null;

        try {
            jsonObj = new JSONObject(json);

            seller = jsonObj.getString("seller");
            buyer = jsonObj.getString("buyer");
            coinSymbol = jsonObj.getString("coinSymbol");
            amountEuro = jsonObj.getDouble("amountEuro");
            amountCoin = jsonObj.getDouble("amountCoin");

            transaction = new Transaction(seller, buyer, coinSymbol, amountEuro, amountCoin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserInfo sellerAcc = userInfoRepository.findByUserName(seller);
        sellerAcc.getAccount().addTransaction(transaction);

        UserInfo buyerAcc = userInfoRepository.findByUserName(buyer);
        buyerAcc.getAccount().addTransaction(transaction);

    }

    @RequestMapping("/test")
    public void testUser(@RequestParam(value = "userName") String userName) {
        userInfoRepository.deleteAll();
        UserInfo.Builder userInfoBuilder = new UserInfo.Builder();
        userInfoBuilder.userName(userName)
                .loggedIn(true)
                .account(new Account.Builder().initBalance().updateCoin("BTC",100).build());
        userInfoRepository.save(userInfoBuilder.build());
    }

}

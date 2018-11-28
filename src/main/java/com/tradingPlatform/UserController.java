package com.tradingPlatform;

import com.tradingPlatform.DataObjects.Account;
import com.tradingPlatform.DataObjects.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam(value = "user") String user) {
        //logged in user, return all data on them
        //testing
        UserInfo userInfo = new UserInfo.Builder()
                .loggedIn(true)
                .userName(user)
                .publicKey("123456789")
                .account(new Account.Builder().currentBalance(10.0).build())
                .build();

        return "User: "+userInfo;
    }




}

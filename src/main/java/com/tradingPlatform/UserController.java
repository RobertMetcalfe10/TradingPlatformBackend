package com.tradingPlatform;

import com.tradingPlatform.DataObjects.Account;
import com.tradingPlatform.DataObjects.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userSetUp")
    public String userInfo(@RequestParam(value = "user") String user) {
        //logged in user, return all data on them
        //testing
        UserInfo userInfo = new UserInfo.Builder()
                .loggedIn(false)
                .userName(user)
                .account(new Account.Builder().initBalance().build())
                .build();

        return "User: "+userInfo;
    }




}

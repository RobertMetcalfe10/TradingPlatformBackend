package com.tradingPlatform;

import com.tradingPlatform.DataObjects.Account;
import com.tradingPlatform.DataObjects.UserInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class UserInfoController {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "user");
        SpringApplication.run(UserInfoController.class, args);
    }

}


@RestController
@RequestMapping("/userInfo")
class UserInfoRestController {


    private UserInfoRepository userInfoRepository;


    public UserInfoRestController (UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @RequestMapping("/user")
    public String userInfo(@RequestParam(value = "userName") String userName) {
        return userInfoRepository.findByUserName(userName).toString();
    }

    @RequestMapping("/test")
    public void testUser(@RequestParam(value = "userName") String userName) {
        UserInfo.Builder userInfoBuilder = new UserInfo.Builder();
        userInfoBuilder.userName(userName)
                .loggedIn(true)
                .account(new Account.Builder().currentBalance(125).build());
        userInfoRepository.save(userInfoBuilder.build());
    }

}

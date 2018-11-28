package com.tradingPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient        // It acts as a eureka client
@EnableZuulProxy        // Enable Zuul

public class ZuulApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "zuul");
        SpringApplication.run(ZuulApplication.class, args);
    }
}
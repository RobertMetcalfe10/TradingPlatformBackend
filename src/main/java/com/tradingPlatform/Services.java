package com.tradingPlatform;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {JmxAutoConfiguration.class})
public class Services {

    public static void main(String[] args) {
        CoinMarketCapController.main(args);
        EurekaClientApplication.main(args);
    }
}

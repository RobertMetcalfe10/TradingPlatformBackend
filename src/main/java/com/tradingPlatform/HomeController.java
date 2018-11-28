package com.tradingPlatform;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    private static final String template = "Hello, %s!";

    @RequestMapping("")
    public String home(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }
}
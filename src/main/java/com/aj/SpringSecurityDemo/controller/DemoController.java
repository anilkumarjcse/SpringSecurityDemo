package com.aj.SpringSecurityDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/msg")
    public String getMessage(){
        return "Welcome to Spring Security Demo";
    }
}

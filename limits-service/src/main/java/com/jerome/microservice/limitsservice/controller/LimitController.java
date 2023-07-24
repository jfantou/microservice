package com.jerome.microservice.limitsservice.controller;

import com.jerome.microservice.limitsservice.bean.Limits;
import com.jerome.microservice.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limits retrieveLimits(){
        return Limits.builder().maximum(configuration.getMaximum()).minimum(configuration.getMinimum()).build();
    }
}

package com.jerome.microservice.currencyexchangeservice.controller;

import com.jerome.microservice.currencyexchangeservice.model.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to){
        return new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50), environment.getProperty("local.server.port"));
    }

}

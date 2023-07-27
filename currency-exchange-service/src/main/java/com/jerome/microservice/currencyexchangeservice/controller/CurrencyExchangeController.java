package com.jerome.microservice.currencyexchangeservice.controller;

import com.jerome.microservice.currencyexchangeservice.model.CurrencyExchange;
import com.jerome.microservice.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to){
        Optional<CurrencyExchange> currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        CurrencyExchange rst = currencyExchange.get();
        rst.setEnvironment(environment.getProperty("local.server.port"));
        return rst;
    }

    @PostMapping("currency-exchanges")
    public CurrencyExchange saveCurrencyExchange(@RequestBody CurrencyExchange currencyExchange){
        return currencyExchangeRepository.save(currencyExchange);
    }

}

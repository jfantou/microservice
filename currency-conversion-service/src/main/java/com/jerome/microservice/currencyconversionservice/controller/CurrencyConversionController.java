package com.jerome.microservice.currencyconversionservice.controller;

import com.jerome.microservice.currencyconversionservice.model.CurrencyConversion;
import com.jerome.microservice.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
        CurrencyConversion rstCall = responseEntity.getBody();
        CurrencyConversion rst = CurrencyConversion.builder().id(rstCall.getId())
                .from(from)
                .to(to)
                .conversionMultiple(rstCall.getConversionMultiple())
                .totalCalculatedAmount(quantity.multiply(rstCall.getConversionMultiple()))
                .environment(rstCall.getEnvironment())
                .quantity(quantity)
                .build();
        return rst;
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        CurrencyConversion rstCall = currencyExchangeProxy.retrieveExchangeValue(from, to);
        CurrencyConversion rst = CurrencyConversion.builder().id(rstCall.getId())
                .from(from)
                .to(to)
                .conversionMultiple(rstCall.getConversionMultiple())
                .totalCalculatedAmount(quantity.multiply(rstCall.getConversionMultiple()))
                .environment(rstCall.getEnvironment())
                .quantity(quantity)
                .build();
        return rst;
    }

}

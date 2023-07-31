package com.jerome.microservice.currencyexchangeservice.controller;

import com.jerome.microservice.currencyexchangeservice.model.CurrencyExchange;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class CircuitBreakerExamplecontroller {
    private Logger logger = LoggerFactory.getLogger(CircuitBreakerExamplecontroller.class);

    @GetMapping("/sample-api")
    //@Retry(name = "default", fallbackMethod = "fallbackResponse")
    @CircuitBreaker(name="default", fallbackMethod = "fallbackResponse")
    public String retrieveExchangeValue(){
        logger.info("Call other url");
        new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return "Sample API";
    }

    public String fallbackResponse(Exception ex){
        return "fallbackResponse";
    }

    @GetMapping("/sample-api-rate")
    @RateLimiter(name = "default")
    public String sampleRateLimit(){
        return "Sample API";
    }

    @GetMapping("/sample-api-bulkhead")
    @Bulkhead(name = "default")
    public String sampleBulkhead() throws InterruptedException {
        logger.info("Call endpont");
        new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return "Sample API";
    }


}

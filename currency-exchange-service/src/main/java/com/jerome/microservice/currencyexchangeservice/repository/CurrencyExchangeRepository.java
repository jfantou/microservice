package com.jerome.microservice.currencyexchangeservice.repository;

import com.jerome.microservice.currencyexchangeservice.model.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    Optional<CurrencyExchange> findByFromAndTo(String from, String to);
}

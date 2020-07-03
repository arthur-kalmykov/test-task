package com.itembase.testtask.controller;

import com.itembase.testtask.model.ConversionRequest;
import com.itembase.testtask.util.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    @Autowired
    private CurrencyConverter currencyConverter;

    @PostMapping
    public Mono<ResponseEntity<?>> convert(@RequestBody ConversionRequest request) {
        return currencyConverter.convert(request)
                .map(ResponseEntity::ok);
    }
}

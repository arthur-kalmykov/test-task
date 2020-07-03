package com.itembase.testtask.util.impl;

import com.itembase.testtask.controller.exception.ExternalApiException;
import com.itembase.testtask.model.ConversionRequest;
import com.itembase.testtask.model.ConversionResponse;
import com.itembase.testtask.model.external.ExchangeRateResponse;
import com.itembase.testtask.util.CurrencyConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Random;

@Component
@Slf4j
public class CurrencyConverterImpl implements CurrencyConverter {

    public static final String[] EXCHANGE_RATES_APIS = {"https://api.exchangeratesapi.io/latest?base=",
            "https://api.exchangerate-api.com/v4/latest/"};

    @Autowired
    private WebClient webClient;

    private final Random random = new Random();

    @Override
    public Mono<ConversionResponse> convert(ConversionRequest request) {
        int select = random.nextInt(EXCHANGE_RATES_APIS.length); //takes random from 2 possible apis
        int another = Math.abs(select - 1); //another (not selected api)
        return webClient.get()
                .uri(EXCHANGE_RATES_APIS[select] + request.getFrom())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve() //trying to read from the first api
                .bodyToMono(ExchangeRateResponse.class)
                .onErrorResume( e -> {
                    log.debug("api {} is not available at the moment", EXCHANGE_RATES_APIS[select]);
                    return webClient.get()
                            .uri(EXCHANGE_RATES_APIS[another] + request.getFrom()) //trying to read from second api
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(ExchangeRateResponse.class)
                            .onErrorResume(error -> Mono.error(new ExternalApiException(""))); //if error - we a trowing 503 status
                })
                .map(response -> new ConversionResponse(request, response));
    }
}

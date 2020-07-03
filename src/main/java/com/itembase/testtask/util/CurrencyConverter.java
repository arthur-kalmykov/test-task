package com.itembase.testtask.util;

import com.itembase.testtask.model.ConversionRequest;
import com.itembase.testtask.model.ConversionResponse;
import reactor.core.publisher.Mono;

public interface CurrencyConverter {
    Mono<ConversionResponse> convert(ConversionRequest request);
}

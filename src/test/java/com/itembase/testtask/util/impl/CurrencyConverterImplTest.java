package com.itembase.testtask.util.impl;

import com.itembase.testtask.controller.exception.ExternalApiException;
import com.itembase.testtask.model.ConversionRequest;
import com.itembase.testtask.model.ConversionResponse;
import com.itembase.testtask.model.enums.Currency;
import com.itembase.testtask.util.CurrencyConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ComponentScan("com.itembase")
public class CurrencyConverterImplTest {

    @Autowired
    private CurrencyConverter currencyConverter;

    @Autowired
    ApplicationContext context;

    @Test
    void testServicesUnavailable() throws Exception {

        final String[] EXCHANGE_RATES_APIS = {"https://api.exchangeratesapi111.io/latest?base=",
                "https://api.exchangerate-api111.com/v4/latest/"};

        setFinalStatic(CurrencyConverterImpl.class.getDeclaredField("EXCHANGE_RATES_APIS"), EXCHANGE_RATES_APIS);
        Mono<ConversionResponse> result = currencyConverter.convert(testRequest());

        assertThatThrownBy(() -> currencyConverter.convert(testRequest()).block())
                .isExactlyInstanceOf(ExternalApiException.class);
    }

    @Test
    void testOnlyOneServiceUnavailable() throws Exception {

        final String[] EXCHANGE_RATES_APIS = {"https://api.exchangeratesapi111.io/latest?base=",
                "https://api.exchangerate-api.com/v4/latest/"};

        setFinalStatic(CurrencyConverterImpl.class.getDeclaredField("EXCHANGE_RATES_APIS"), EXCHANGE_RATES_APIS);
        ConversionResponse result = currencyConverter.convert(testRequest()).block();

        assertNotNull(result);

    }

    private ConversionRequest testRequest() {
        ConversionRequest result = new ConversionRequest();
        result.setAmount(10.0);
        result.setFrom(Currency.USD);
        result.setTo(Currency.EUR);
        return result;
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

}

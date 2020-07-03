package com.itembase.testtask.model;

import com.itembase.testtask.model.external.ExchangeRateResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ConversionResponse extends AbstractConversion {
    private Double converted;

    public ConversionResponse(ConversionRequest conversionRequest, ExchangeRateResponse exchangeRateResponse) {
        this.setAmount(conversionRequest.getAmount()) ;
        this.setFrom(conversionRequest.getFrom());
        this.setTo(conversionRequest.getTo());
        this.setConverted(conversionRequest.getAmount() * exchangeRateResponse.getRates().get(conversionRequest.getTo()));
    }
}

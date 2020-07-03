package com.itembase.testtask.model;

import com.itembase.testtask.model.enums.Currency;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class AbstractConversion {
    private Currency from;
    private Currency to;
    private Double amount;
}

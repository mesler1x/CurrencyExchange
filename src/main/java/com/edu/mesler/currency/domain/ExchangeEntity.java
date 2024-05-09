package com.edu.mesler.currency.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExchangeEntity {
    int id;
    CurrencyEntity baseCurrency;
    CurrencyEntity targetCurrency;
    double rate;
}

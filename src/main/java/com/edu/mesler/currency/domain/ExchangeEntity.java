package com.edu.mesler.currency.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeEntity {
    int id;
    CurrencyEntity baseCurrency;
    CurrencyEntity targetCurrency;
    double rate;
}

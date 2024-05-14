package com.edu.mesler.currency.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeEntity {
    int id;
    CurrencyEntity baseCurrency;
    CurrencyEntity targetCurrency;
    double rate;
}

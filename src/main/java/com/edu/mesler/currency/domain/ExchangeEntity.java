package com.edu.mesler.currency.domain;

public class ExchangeEntity {
    int id;
    CurrencyEntity baseCurrencyId;
    CurrencyEntity targetCurrencyId;
    double rate;
}

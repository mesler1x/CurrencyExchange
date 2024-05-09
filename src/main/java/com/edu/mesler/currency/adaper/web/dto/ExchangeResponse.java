package com.edu.mesler.currency.adaper.web.dto;

public record ExchangeResponse(int id,
                               CurrencyResponse baseCurrency,
                               CurrencyResponse targetCurrency,
                               double rate) {
}

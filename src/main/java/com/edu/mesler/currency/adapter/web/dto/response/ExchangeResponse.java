package com.edu.mesler.currency.adapter.web.dto.response;

public record ExchangeResponse(int id,
                               CurrencyResponse baseCurrency,
                               CurrencyResponse targetCurrency,
                               double rate) {
}

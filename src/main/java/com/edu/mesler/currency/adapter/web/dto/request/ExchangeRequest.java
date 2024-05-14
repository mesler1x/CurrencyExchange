package com.edu.mesler.currency.adapter.web.dto.request;

public record ExchangeRequest(int baseCurrency, int targetCurrency,
                              double rate) {
}

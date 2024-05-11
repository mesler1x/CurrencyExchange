package com.edu.mesler.currency.adaper.web.dto.request;

public record ExchangeRequest(int baseCurrency, int targetCurrency,
                              double rate) {
}

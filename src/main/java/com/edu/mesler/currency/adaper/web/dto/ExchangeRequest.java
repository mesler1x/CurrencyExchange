package com.edu.mesler.currency.adaper.web.dto;

public record ExchangeRequest(int baseCurrency, int targetCurrency,
                              double rate) {
}

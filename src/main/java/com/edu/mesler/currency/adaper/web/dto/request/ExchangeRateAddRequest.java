package com.edu.mesler.currency.adaper.web.dto.request;

public record ExchangeRateAddRequest(String baseCurrencyCode, String targetCurrencyCode, double rate) {
}

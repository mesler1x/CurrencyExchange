package com.edu.mesler.currency.adapter.web.dto.request;

public record ExchangeRateAddRequest(String baseCurrencyCode, String targetCurrencyCode, double rate) {
}

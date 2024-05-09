package com.edu.mesler.currency.adaper.web.dto;

public record ExchangeRateAddRequest(String baseCurrencyCode, String targetCurrencyCode, double rate) {
}

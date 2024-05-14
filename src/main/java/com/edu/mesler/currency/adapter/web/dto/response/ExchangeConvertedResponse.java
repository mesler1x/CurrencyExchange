package com.edu.mesler.currency.adapter.web.dto.response;

public record ExchangeConvertedResponse(CurrencyResponse baseCurrency,
                                        CurrencyResponse targetCurrency,
                                        double rate,
                                        double amount,
                                        double convertedAmount) {
}

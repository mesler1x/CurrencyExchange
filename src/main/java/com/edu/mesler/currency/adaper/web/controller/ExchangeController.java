package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.web.dto.ExchangeRateAddRequest;
import com.edu.mesler.currency.adaper.web.dto.ExchangeResponse;
import com.edu.mesler.currency.service.ExchangeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ExchangeController {
    ExchangeService exchangeService;

    @GetMapping("/exchangeRates")
    public List<ExchangeResponse> getAllExchanges() {
        return exchangeService.getAllExchanges();
    }

    @PostMapping("/exchangeRates")
    public ExchangeRateAddRequest createExchangeRate(@RequestBody ExchangeRateAddRequest exchangeRateAddRequest) {
        return exchangeService.addNewExchangeRate(exchangeRateAddRequest);
    }
}

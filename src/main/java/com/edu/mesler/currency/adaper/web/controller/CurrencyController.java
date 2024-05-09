package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import com.edu.mesler.currency.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CurrencyController {
    CurrencyService currencyService;

    @GetMapping("/currencies")
    public List<CurrencyResponse> getAll() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/currency/{code}")
    public CurrencyResponse getOne(@PathVariable String code) {
        return currencyService.getCurrencyByCode(code);
    }

    @PostMapping("/currencies")
    public CurrencyResponse create(@RequestBody CurrencyRequest currencyRequest) {
        return currencyService.createCurrency(currencyRequest);
    }
}

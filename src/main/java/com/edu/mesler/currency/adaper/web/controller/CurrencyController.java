package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.web.dto.request.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.response.CurrencyResponse;
import com.edu.mesler.currency.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
    public ResponseEntity<CurrencyResponse> create(@RequestBody CurrencyRequest currencyRequest) {
        return new ResponseEntity<>(currencyService.createCurrency(currencyRequest), HttpStatus.CREATED) ;
    }
}

package com.edu.mesler.currency.adapter.web.controller;

import com.edu.mesler.currency.adapter.web.dto.request.CurrencyRequest;
import com.edu.mesler.currency.adapter.web.dto.response.CurrencyResponse;
import com.edu.mesler.currency.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер валют.", description = "Контроллер для управления валютой.")
public class CurrencyController {
    CurrencyService currencyService;

    @Operation(
            summary = "Просмотр всех валют.",
            description = "Ендпоинт отдаёт все валюты, котрые находятся в базе данных."
    )
    @GetMapping("/currencies")
    public List<CurrencyResponse> getAll() {
        return currencyService.getAllCurrencies();
    }

    @Operation(
            summary = "Просмотр конкретной валюты валюты.",
            description = "Просмотр валюты по её трёхсимвольному коду в формате - GET /currency/USD"
    )
    @GetMapping("/currency/{code}")
    public CurrencyResponse getOne(@PathVariable String code) {
        return currencyService.getCurrencyByCode(code);
    }

    @Operation(
            summary = "Создание валюты.",
            description = "Ендпоинт для создания новой валюты."
    )
    @PostMapping(value = "/currencies", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<CurrencyResponse> create(@RequestParam String name, @RequestParam String code, @RequestParam String sign) {
        CurrencyRequest currencyRequest = new CurrencyRequest(name, code, sign);
        return new ResponseEntity<>(currencyService.createCurrency(currencyRequest), HttpStatus.CREATED);
    }
}

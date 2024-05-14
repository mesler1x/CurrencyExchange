package com.edu.mesler.currency.adapter.web.controller;

import com.edu.mesler.currency.adapter.web.dto.request.ExchangeEditRequest;
import com.edu.mesler.currency.adapter.web.dto.request.ExchangeRateAddRequest;
import com.edu.mesler.currency.adapter.web.dto.response.ExchangeConvertedResponse;
import com.edu.mesler.currency.adapter.web.dto.response.ExchangeResponse;
import com.edu.mesler.currency.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Tag(name = "Контроллер обмена валют.", description = "Контроллер, позволяющий управлять обменными курсами валют.")
public class ExchangeController {
    ExchangeService exchangeService;

    @Operation(
            summary = "Просмотр всех обменных курсов.",
            description = "Получение списка всех обменных курсов."
    )
    @GetMapping("/exchangeRates")
    public List<ExchangeResponse> getAllExchanges() {
        return exchangeService.getAllExchanges();
    }

    @Operation(
            summary = "Получение конкретного обменного курса.",
            description = "Получение конкретного обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса. Пример - GET /exchangeRate/USDRUB"
    )
    @GetMapping("/exchangeRate/{codes}")
    public ExchangeResponse getExchangeRate(@PathVariable String codes) {
        return exchangeService.getExchangeByCodes(codes);
    }

    @Operation(
            summary = "Создание нового обменного курса.",
            description = "Добавление нового обменного курса в базу."
    )
    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeResponse> createExchangeRate(@RequestBody ExchangeRateAddRequest exchangeRateAddRequest) {
        return new ResponseEntity<>(exchangeService.addNewExchangeRate(exchangeRateAddRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление существующего обменного курса",
            description = "Обновление существующего в базе обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса."
    )
    @PatchMapping("/exchangeRate/{codes}")
    public ExchangeResponse editExistingExchangeRate(@PathVariable String codes, @RequestBody ExchangeEditRequest exchangeEditRequest) {
        return exchangeService.editExchangeRate(codes, exchangeEditRequest.rate());
    }

    @Operation(
            summary = "Расчёт перевода определённого количества средств из одной валюты в другую.",
            description = "Пример запроса - GET /exchange?from=USD&to=AUD&amount=10"
    )
    @GetMapping("/exchange")
    public ExchangeConvertedResponse getExchangeRateAnyAmount(@RequestParam String from, @RequestParam String to, @RequestParam String amount) {
        return exchangeService.getExchangeAnyAmount(from, to, amount);
    }
}


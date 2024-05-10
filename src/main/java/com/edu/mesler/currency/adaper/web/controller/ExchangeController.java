package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.web.dto.ExchangeRateAddRequest;
import com.edu.mesler.currency.adaper.web.dto.ExchangeResponse;
import com.edu.mesler.currency.service.ExchangeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/exchangeRate/{codes}")
    public ExchangeResponse getExchangeRate(@PathVariable String codes) {
        return exchangeService.getExchangeByCodes(codes);
    }

    @PostMapping("/exchangeRates")
    public ExchangeResponse createExchangeRate(@RequestBody ExchangeRateAddRequest exchangeRateAddRequest) {
        return exchangeService.addNewExchangeRate(exchangeRateAddRequest);
    }
}

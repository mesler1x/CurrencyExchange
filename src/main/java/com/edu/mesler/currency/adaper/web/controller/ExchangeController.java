package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.web.dto.request.ExchangeEditRequest;
import com.edu.mesler.currency.adaper.web.dto.request.ExchangeRateAddRequest;
import com.edu.mesler.currency.adaper.web.dto.response.ExchangeConvertedResponse;
import com.edu.mesler.currency.adaper.web.dto.response.ExchangeResponse;
import com.edu.mesler.currency.service.ExchangeService;
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
    public ResponseEntity<ExchangeResponse> createExchangeRate(@RequestBody ExchangeRateAddRequest exchangeRateAddRequest) {
        return new ResponseEntity<>(exchangeService.addNewExchangeRate(exchangeRateAddRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/exchangeRate/{codes}")
    public ExchangeResponse editExistingExchangeRate(@PathVariable String codes, @RequestBody ExchangeEditRequest exchangeEditRequest) {
        return exchangeService.editExchangeRate(codes, exchangeEditRequest.rate());
    }

    @GetMapping("/exchange")
    public ExchangeConvertedResponse getExchangeRateAnyAmount(@RequestParam String from, @RequestParam String to, @RequestParam String amount) {
        return exchangeService.getExchangeAnyAmount(from, to, amount);
    }
}


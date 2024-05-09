package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.web.dto.ExchangeRequest;
import com.edu.mesler.currency.adaper.web.dto.ExchangeResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ExchangeController {

    @GetMapping("/exchangeRates")
    public List<ExchangeResponse> getAll(@RequestBody ExchangeRequest exchangeRequest) {
        return
    }
}

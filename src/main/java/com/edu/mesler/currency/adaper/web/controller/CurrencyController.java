package com.edu.mesler.currency.adaper.web.controller;

import com.edu.mesler.currency.adaper.repository.CurrencyRepository;
import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CurrencyController {
    CurrencyRepository currencyRepository;

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyResponse>> getAll() {
        return ResponseEntity.ok().body(currencyRepository.getAll());
    }

    @GetMapping("/currency/{code}")
    public CurrencyResponse getOne(@PathVariable String code) {
        return null;
    }

    @PostMapping("/currencies")
    public CurrencyResponse create(@RequestBody CurrencyRequest currencyRequest) {
        return null;
    }
}

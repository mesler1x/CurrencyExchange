package com.edu.mesler.currency.service;

import com.edu.mesler.currency.adapter.repository.CurrencyRepository;
import com.edu.mesler.currency.service.mapper.CurrencyMapper;
import com.edu.mesler.currency.adapter.web.dto.request.CurrencyRequest;
import com.edu.mesler.currency.adapter.web.dto.response.CurrencyResponse;
import com.edu.mesler.currency.domain.CurrencyEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CurrencyService {
    CurrencyRepository currencyRepository;
    CurrencyMapper currencyMapper;

    public List<CurrencyResponse> getAllCurrencies() {
        List<CurrencyEntity> queryResult = currencyRepository.getAll();
        List<CurrencyResponse> response = new ArrayList<>();
        for(CurrencyEntity currencyEntity : queryResult) {
            CurrencyResponse currencyResponse = currencyMapper.entityToResponse(currencyEntity);
            response.add(currencyResponse);
        }

        return response;
    }

    public CurrencyResponse getCurrencyByCode(String code) {
        CurrencyEntity queryResult = currencyRepository.getOneByCode(code);
        return currencyMapper.entityToResponse(queryResult);
    }

    public CurrencyResponse createCurrency(CurrencyRequest currencyRequest) {
        CurrencyEntity queryResult = currencyRepository.create(currencyRequest);
        return currencyMapper.entityToResponse(queryResult);
    }
}

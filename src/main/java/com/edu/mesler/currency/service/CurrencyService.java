package com.edu.mesler.currency.service;

import com.edu.mesler.currency.adaper.repository.CurrencyRepository;
import com.edu.mesler.currency.adaper.repository.mapper.CurrencyMapper;
import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
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

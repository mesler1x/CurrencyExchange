package com.edu.mesler.currency.service;

import com.edu.mesler.currency.adaper.repository.CurrencyRepository;
import com.edu.mesler.currency.adaper.repository.ExchangeRepository;
import com.edu.mesler.currency.adaper.repository.mapper.ExchangeMapper;
import com.edu.mesler.currency.adaper.web.dto.ExchangeRateAddRequest;
import com.edu.mesler.currency.adaper.web.dto.ExchangeRequest;
import com.edu.mesler.currency.adaper.web.dto.ExchangeResponse;
import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeService {
    ExchangeRepository exchangeRepository;
    CurrencyRepository currencyRepository;
    ExchangeMapper exchangeMapper;

    public List<ExchangeResponse> getAllExchanges() {
        List<ExchangeEntity> queryResult = exchangeRepository.getAll();
        List<ExchangeResponse> response = new ArrayList<>();
        for (ExchangeEntity entity : queryResult) {
            response.add(exchangeMapper.entityToResponse(entity));
        }

        return response;
    }

    public ExchangeResponse addNewExchangeRate(ExchangeRateAddRequest exchangeRateAddRequest) {

        CurrencyEntity baseCurrency = currencyRepository.getOneByCode(exchangeRateAddRequest.baseCurrencyCode());
        CurrencyEntity targetCurrency = currencyRepository.getOneByCode(exchangeRateAddRequest.targetCurrencyCode());

        ExchangeRequest exchangeRequest = new ExchangeRequest(baseCurrency.getId(), targetCurrency.getId(), exchangeRateAddRequest.rate());
        ExchangeEntity exchangeEntity = exchangeRepository.save(exchangeRequest);


        return exchangeMapper.entityToResponse(exchangeEntity);
    }
}

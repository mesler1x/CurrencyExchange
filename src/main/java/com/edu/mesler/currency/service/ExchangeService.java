package com.edu.mesler.currency.service;

import com.edu.mesler.currency.adaper.repository.CurrencyRepository;
import com.edu.mesler.currency.adaper.repository.ExchangeRepository;
import com.edu.mesler.currency.adaper.web.exception.ClientException;
import com.edu.mesler.currency.service.mapper.ExchangeMapper;
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

        if (exchangeRateAddRequest == null || exchangeRateAddRequest.rate() < 0 ||
                exchangeRateAddRequest.baseCurrencyCode() == null || exchangeRateAddRequest.baseCurrencyCode().length() > 3
        || exchangeRateAddRequest.targetCurrencyCode() == null || exchangeRateAddRequest.targetCurrencyCode().length() > 3 ||
        exchangeRateAddRequest.targetCurrencyCode().equals(exchangeRateAddRequest.baseCurrencyCode())) {
            throw new ClientException("Exchange add request");
        }

        CurrencyEntity baseCurrency = currencyRepository.getOneByCode(exchangeRateAddRequest.baseCurrencyCode());
        CurrencyEntity targetCurrency = currencyRepository.getOneByCode(exchangeRateAddRequest.targetCurrencyCode());
        


        ExchangeRequest exchangeRequest = new ExchangeRequest(baseCurrency.getId(), targetCurrency.getId(), exchangeRateAddRequest.rate());
        ExchangeEntity exchangeEntity = exchangeRepository.save(exchangeRequest);


        return exchangeMapper.entityToResponse(exchangeEntity);
    }

    public ExchangeResponse getExchangeByCodes(String codes) {
        String baseCode = codes.substring(0,3);
        String targetCode = codes.substring(3);

        CurrencyEntity baseCurrencyEntity = currencyRepository.getOneByCode(baseCode);
        CurrencyEntity targetCurrencyEntity = currencyRepository.getOneByCode(targetCode);

        int baseCurrencyId = baseCurrencyEntity.getId();
        int targetCurrencyId = targetCurrencyEntity.getId();

        ExchangeEntity exchangeEntity = exchangeRepository.findExchangeByTwoCodesIds(baseCurrencyId, targetCurrencyId);

        return exchangeMapper.entityToResponse(exchangeEntity);
    }
}

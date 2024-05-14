package com.edu.mesler.currency.service;

import com.edu.mesler.currency.adaper.repository.CurrencyRepository;
import com.edu.mesler.currency.adaper.repository.ExchangeRepository;
import com.edu.mesler.currency.adaper.web.dto.request.ExchangeRateAddRequest;
import com.edu.mesler.currency.adaper.web.dto.response.ExchangeConvertedResponse;
import com.edu.mesler.currency.adaper.web.dto.response.ExchangeResponse;
import com.edu.mesler.currency.adaper.web.exception.ClientException;
import com.edu.mesler.currency.adaper.web.exception.NotFoundException;
import com.edu.mesler.currency.domain.ExchangeEntity;
import com.edu.mesler.currency.service.mapper.CurrencyMapper;
import com.edu.mesler.currency.service.mapper.ExchangeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeService {
    ExchangeRepository exchangeRepository;
    ExchangeMapper exchangeMapper;
    CurrencyMapper currencyMapper;

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

        ExchangeEntity exchangeEntity = exchangeRepository.save(exchangeRateAddRequest.baseCurrencyCode(),
                exchangeRateAddRequest.baseCurrencyCode(),
                exchangeRateAddRequest.rate());


        return exchangeMapper.entityToResponse(exchangeEntity);
    }

    public ExchangeResponse getExchangeByCodes(String codes) {
        String baseCode = codes.substring(0, 3);
        String targetCode = codes.substring(3);

        Optional<ExchangeEntity> exchangeEntity = exchangeRepository.findExchangeByTwoCodes(baseCode, targetCode);

        if (exchangeEntity.isEmpty()) throw new NotFoundException("Exchange");
        return exchangeMapper.entityToResponse(exchangeEntity.get());
    }

    public ExchangeResponse editExchangeRate(String codes, double rate) {
        String baseCode = codes.substring(0, 3);
        String targetCode = codes.substring(3);
        ExchangeEntity exchangeEntity = exchangeRepository.updateExchangeRate(baseCode, targetCode, rate);
        return exchangeMapper.entityToResponse(exchangeEntity);
    }

    public ExchangeConvertedResponse getExchangeAnyAmount(String baseCurrencyCode, String targetCurrencyCode, String inAmount) {
        double amount = Double.parseDouble(inAmount);
        double convertedAmount;
        Optional<ExchangeEntity> exchange = exchangeRepository.findExchangeByTwoCodes(baseCurrencyCode, targetCurrencyCode);
        if (exchange.isPresent()) {
            ExchangeEntity exchangeEntity = exchange.get();
            convertedAmount = exchangeEntity.getRate() * amount;

            amount = roundNumberToResponse(amount);
            convertedAmount = roundNumberToResponse(convertedAmount);
            return exchangeMapper.entityToConvertedResponse(exchangeEntity.getRate(),
                    currencyMapper.entityToResponse(exchangeEntity.getBaseCurrency()),
                    currencyMapper.entityToResponse(exchangeEntity.getTargetCurrency()),
                    amount,
                    convertedAmount);
        }

        exchange = exchangeRepository.findExchangeByTwoCodes(targetCurrencyCode, baseCurrencyCode);

        if (exchange.isPresent()) {

            ExchangeEntity exchangeEntity = exchange.get();
            double rate = 1 / exchangeEntity.getRate();
            convertedAmount = amount / rate;
            amount = roundNumberToResponse(amount);
            convertedAmount = roundNumberToResponse(convertedAmount);

            return exchangeMapper.entityToConvertedResponse(exchangeEntity.getRate(),
                    currencyMapper.entityToResponse(exchangeEntity.getTargetCurrency()),
                    currencyMapper.entityToResponse(exchangeEntity.getBaseCurrency()),
                    amount,
                    convertedAmount);
        }

        Optional<ExchangeEntity> exchangeUSDA = exchangeRepository.findExchangeByTwoCodes("USD", baseCurrencyCode);
        Optional<ExchangeEntity> exchangeUSDB = exchangeRepository.findExchangeByTwoCodes("USD", targetCurrencyCode);

        if (exchangeUSDA.isPresent() && exchangeUSDB.isPresent()) {
            ExchangeEntity usdA = exchangeUSDA.get();
            ExchangeEntity usdB = exchangeUSDB.get();

            double rateAtoB = (1 / usdA.getRate()) * usdB.getRate();
            convertedAmount = rateAtoB * amount;

            amount = roundNumberToResponse(amount);
            convertedAmount = roundNumberToResponse(convertedAmount);
            rateAtoB = roundNumberToResponse(rateAtoB);

            ExchangeEntity exchangeEntity = new ExchangeEntity();
            exchangeEntity.setBaseCurrency(usdA.getTargetCurrency());
            exchangeEntity.setTargetCurrency(usdB.getTargetCurrency());

            return exchangeMapper.entityToConvertedResponse(rateAtoB,
                    currencyMapper.entityToResponse(exchangeEntity.getBaseCurrency()),
                    currencyMapper.entityToResponse(exchangeEntity.getTargetCurrency()),
                    amount,
                    convertedAmount);
        }

        throw new NotFoundException("Exchange");
    }

    private static double roundNumberToResponse(double amount) {
        amount = Math.round(amount * 100.0) / 100.0;
        return amount;
    }
}

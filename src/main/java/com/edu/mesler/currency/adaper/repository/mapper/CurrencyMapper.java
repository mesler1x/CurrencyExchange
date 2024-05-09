package com.edu.mesler.currency.adaper.repository.mapper;

import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import com.edu.mesler.currency.domain.CurrencyEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public CurrencyResponse entityToResponse(CurrencyEntity currencyEntity) {
        return new CurrencyResponse(
                currencyEntity.getId(),
                currencyEntity.getFullName(),
                currencyEntity.getCode(),
                currencyEntity.getSign());
    }
}

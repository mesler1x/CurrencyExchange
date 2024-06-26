package com.edu.mesler.currency.service.mapper;

import com.edu.mesler.currency.adapter.web.dto.response.CurrencyResponse;
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

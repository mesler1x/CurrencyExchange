package com.edu.mesler.currency.adaper.repository.mapper;

import com.edu.mesler.currency.adaper.web.dto.ExchangeResponse;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExchangeMapper {
    CurrencyMapper currencyMapper;

    public ExchangeResponse entityToResponse(ExchangeEntity entity) {
        return new ExchangeResponse(
                entity.getId(),
                currencyMapper.entityToResponse(entity.getBaseCurrency()),
                currencyMapper.entityToResponse(entity.getTargetCurrency()),
                entity.getRate()
        );
    }
}

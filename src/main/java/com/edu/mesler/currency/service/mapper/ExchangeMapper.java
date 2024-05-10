package com.edu.mesler.currency.service.mapper;

import com.edu.mesler.currency.adaper.web.dto.ExchangeResponse;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
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

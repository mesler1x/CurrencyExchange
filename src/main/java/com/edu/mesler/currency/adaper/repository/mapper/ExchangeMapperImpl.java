package com.edu.mesler.currency.adaper.repository.mapper;

import com.edu.mesler.currency.adaper.repository.CurrencyRepository;
import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeMapperImpl implements RowMapper<ExchangeEntity> {

    CurrencyRepository currencyRepository;
    @Override
    public ExchangeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        int baseId = rs.getInt("basecurrencyid");
        int targetId = rs.getInt("targetcurrencyid");

        CurrencyEntity baseCurrencyEntity = currencyRepository.getOneById(baseId);
        CurrencyEntity targetCurrencyEntity = currencyRepository.getOneById(targetId);

        double rate = rs.getDouble("rate");

        return new ExchangeEntity(id, baseCurrencyEntity, targetCurrencyEntity, rate);
    }
}

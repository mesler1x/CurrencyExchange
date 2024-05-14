package com.edu.mesler.currency.service.mapper;

import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class ExchangeRowMapperImpl implements RowMapper<ExchangeEntity> {
    @Override
    public ExchangeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExchangeEntity exchangeEntity = new ExchangeEntity();
        int id = rs.getInt("id");

        CurrencyEntity baseCurrencyEntity = new CurrencyEntity();
        CurrencyEntity targetCurrencyEntity = new CurrencyEntity();

        baseCurrencyEntity.setId(rs.getInt("bcurrency_id"));
        baseCurrencyEntity.setCode(rs.getString("bcurrency_code"));
        baseCurrencyEntity.setFullName(rs.getString("bcurrency_fullname"));
        baseCurrencyEntity.setSign(rs.getString("bcurrency_sign"));

        targetCurrencyEntity.setId(rs.getInt("tcurrency_id"));
        targetCurrencyEntity.setCode(rs.getString("tcurrency_code"));
        targetCurrencyEntity.setFullName(rs.getString("tcurrency_fullname"));
        targetCurrencyEntity.setSign(rs.getString("tcurrency_sign"));

        exchangeEntity.setId(id);
        exchangeEntity.setRate(rs.getDouble("rate"));
        exchangeEntity.setBaseCurrency(baseCurrencyEntity);
        exchangeEntity.setTargetCurrency(targetCurrencyEntity);

        return exchangeEntity;
    }
}

package com.edu.mesler.currency.service.mapper;

import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.domain.ExchangeEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRowMapperImpl implements RowMapper<ExchangeEntity> {

    @Override
    public ExchangeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");

        int baseId = rs.getInt("basecurrencyid");
        int targetId = rs.getInt("targetcurrencyid");

        double rate = rs.getDouble("rate");
        return new ExchangeEntity(id, new CurrencyEntity(baseId), new CurrencyEntity(targetId), rate);
    }
}

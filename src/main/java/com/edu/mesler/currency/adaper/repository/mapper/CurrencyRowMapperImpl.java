package com.edu.mesler.currency.adaper.repository.mapper;

import com.edu.mesler.currency.domain.CurrencyEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRowMapperImpl implements RowMapper<CurrencyEntity> {
    @Override
    public CurrencyEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String fullName = rs.getString("fullName");
        String sign = rs.getString("sign");
        String code = rs.getString("code");
        return new CurrencyEntity(id, code, fullName, sign);
    }
}

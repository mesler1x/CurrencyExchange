package com.edu.mesler.currency.adaper.repository.mapper;

import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import com.edu.mesler.currency.domain.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String fullName = rs.getString("fullName");
        String sign = rs.getString("sign");
        String code = rs.getString("code");
        return new Currency(id, code, fullName, sign);
    }
}

package com.edu.mesler.currency.adaper.repository.mapper;

import com.edu.mesler.currency.domain.ExchangeEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeMapperImpl implements RowMapper<ExchangeEntity> {
    @Override
    public ExchangeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");

        return null;
    }
}

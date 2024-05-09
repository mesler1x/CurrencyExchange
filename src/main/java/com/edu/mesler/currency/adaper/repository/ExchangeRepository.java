package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.repository.mapper.ExchangeMapperImpl;
import com.edu.mesler.currency.adaper.web.exception.InternalException;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeRepository {
    JdbcTemplate jdbcTemplate;

    public List<ExchangeEntity> getAll() {
        List<ExchangeEntity> queryResult;
        try {
            queryResult = jdbcTemplate.query("SELECT * FROM ExchangeRates", new ExchangeMapperImpl());
        }catch (DataAccessException exception) {
            throw new InternalException("Database");
        }
        return queryResult;
    }
}

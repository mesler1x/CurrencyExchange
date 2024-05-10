package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.web.exception.NotFoundException;
import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.service.mapper.ExchangeRowMapperImpl;
import com.edu.mesler.currency.adaper.web.dto.ExchangeRequest;
import com.edu.mesler.currency.adaper.web.exception.InternalException;
import com.edu.mesler.currency.domain.ExchangeEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeRepository {
    JdbcTemplate jdbcTemplate;
    CurrencyRepository currencyRepository;
    public List<ExchangeEntity> getAll() {
        List<ExchangeEntity> queryResult;
        try {
            queryResult = jdbcTemplate.query("SELECT * FROM ExchangeRates", new ExchangeRowMapperImpl(currencyRepository));
        } catch (DataAccessException exception) {
            throw new InternalException("Database");
        }
        return queryResult;
    }
    //todo - отлов ошибок
    public ExchangeEntity save(ExchangeRequest exchangeRequest) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO ExchangeRates (basecurrencyid, targetcurrencyid, rate) VALUES (?,?,?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1,exchangeRequest.baseCurrency());
                statement.setInt(2, exchangeRequest.targetCurrency());
                statement.setDouble(3, exchangeRequest.rate());
                return statement;
            }
        }, holder);


        Map<String, Object> map = holder.getKeys();
        int id = (int) map.get("id");
        return getById(id);
    }

    public ExchangeEntity getById(int id) {

        ExchangeEntity exchangeEntity = jdbcTemplate
                .query("SELECT * FROM ExchangeRates WHERE id = ?",
                        new Object[]{id}, new ExchangeRowMapperImpl(currencyRepository)).stream().findFirst().orElse(null);

        if (exchangeEntity == null) {
            throw new NotFoundException("Exchange with id - " + id);
        }
        return exchangeEntity;
    }
}

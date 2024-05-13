package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.web.dto.request.ExchangeRequest;
import com.edu.mesler.currency.adaper.web.exception.AlreadyExistException;
import com.edu.mesler.currency.adaper.web.exception.InternalException;
import com.edu.mesler.currency.adaper.web.exception.NotFoundException;
import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.domain.ExchangeEntity;
import com.edu.mesler.currency.service.mapper.ExchangeRowMapperImpl;
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

    public ExchangeEntity save(ExchangeRequest exchangeRequest) {

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        ExchangeEntity checkExchangeEntityExist;
        try {
            checkExchangeEntityExist = jdbcTemplate.query("SELECT * FROM ExchangeRates WHERE baseCurrencyId = ? and targetCurrencyId = ?",
                    new Object[]{exchangeRequest.baseCurrency(), exchangeRequest.targetCurrency()},
                    new ExchangeRowMapperImpl(currencyRepository)).stream().findAny().orElse(null);

        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        if (checkExchangeEntityExist != null) {
            throw new AlreadyExistException("Exchange");
        }

        try{
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
        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }



        Map<String, Object> map = holder.getKeys();
        int id = (int) map.get("id");
        return getById(id);
    }

    public ExchangeEntity getById(int id) {
        ExchangeEntity exchangeEntity;
        try {
            exchangeEntity = jdbcTemplate
                    .query("SELECT * FROM ExchangeRates WHERE id = ?",
                            new Object[]{id}, new ExchangeRowMapperImpl(currencyRepository))
                    .stream().findFirst().orElseThrow(() -> new NotFoundException("Exchange with id - " + id));

        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        return exchangeEntity;
    }

    public ExchangeEntity findExchangeByTwoCodesIds(int baseCurrencyId, int targetCurrencyId) {
        ExchangeEntity exchangeEntity;
        try {
            exchangeEntity = jdbcTemplate.query("SELECT * FROM ExchangeRates WHERE baseCurrencyId = ? and targetCurrencyId = ?",
                    new Object[]{baseCurrencyId, targetCurrencyId},
                    new ExchangeRowMapperImpl(currencyRepository)).stream().findAny().orElseThrow(() -> new NotFoundException("Exchange rate with this codes"));

        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        return exchangeEntity;
    }

    public ExchangeEntity updateExchangeRate(int baseCurrencyId, int targetCurrencyId, double rate) {
        ExchangeEntity exchangeEntity = findExchangeByTwoCodesIds(baseCurrencyId,targetCurrencyId);
        try {
            jdbcTemplate.update("UPDATE exchangerates SET rate = ? WHERE id = ?", rate, exchangeEntity.getId());
        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        exchangeEntity = getById(exchangeEntity.getId());


        return exchangeEntity;
    }
}

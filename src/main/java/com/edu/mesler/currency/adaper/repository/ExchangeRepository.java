package com.edu.mesler.currency.adaper.repository;

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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeRepository {
    JdbcTemplate jdbcTemplate;
    CurrencyRepository currencyRepository;
    public List<ExchangeEntity> getAll() {
        List<ExchangeEntity> queryResult;
        try {
            queryResult = jdbcTemplate.query(
                    "SELECT er.id as id, er.rate as rate, " +
                            "bc.id as bcurrency_id, bc.code as bcurrency_code, bc.fullname as bcurrency_fullname, bc.sign as bcurrency_sign, " +
                            "tc.id as tcurrency_id, tc.code as tcurrency_code, tc.fullname as tcurrency_fullname, tc.sign as tcurrency_sign " +
                            "FROM ExchangeRates er JOIN Currencies bc ON er.basecurrencyid = bc.id JOIN Currencies tc ON er.targetcurrencyid = tc.id",

                    new ExchangeRowMapperImpl());
        } catch (DataAccessException exception) {
            throw new InternalException("Database");
        }
        return queryResult;
    }

    public ExchangeEntity save(String baseCurrencyCode, String targetCurrencyCode, double rate) {

        Optional<ExchangeEntity> exchangeByTwoCodes = findExchangeByTwoCodes(baseCurrencyCode, targetCurrencyCode);
        if (exchangeByTwoCodes.isPresent()) {
            throw new AlreadyExistException("Exchange rate");
        }

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        CurrencyEntity baseEntity = currencyRepository.getOneByCode(baseCurrencyCode);
        CurrencyEntity targetEntity = currencyRepository.getOneByCode(targetCurrencyCode);

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement("INSERT INTO ExchangeRates (basecurrencyid, targetcurrencyid, rate) VALUES (?,?,?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, baseEntity.getId());
                statement.setInt(2, targetEntity.getId());
                statement.setDouble(3, rate);
                return statement;
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
                    .query("SELECT er.id as id, er.rate as rate, " +
                                    "bc.id as bcurrency_id, bc.code as bcurrency_code, bc.fullname as bcurrency_fullname, bc.sign as bcurrency_sign, " +
                                    "tc.id as tcurrency_id, tc.code as tcurrency_code, tc.fullname as tcurrency_fullname, tc.sign as tcurrency_sign " +
                                    "FROM ExchangeRates er JOIN Currencies bc ON er.basecurrencyid = bc.id JOIN Currencies tc ON er.targetcurrencyid = tc.id " +
                                    "WHERE er.id = ?",
                            new Object[]{id}, new ExchangeRowMapperImpl())
                    .stream().findFirst().orElseThrow(() -> new NotFoundException("Exchange with id - " + id));
        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        return exchangeEntity;
    }

    public Optional<ExchangeEntity> findExchangeByTwoCodes(String baseCurrencyCode, String targetCurrencyCode) {
        Optional<ExchangeEntity> exchangeEntity;
        try {
            exchangeEntity = jdbcTemplate.query("SELECT er.id as id, er.rate as rate, " +
                            "bc.id as bcurrency_id, bc.code as bcurrency_code, bc.fullname as bcurrency_fullname, bc.sign as bcurrency_sign, " +
                            "tc.id as tcurrency_id, tc.code as tcurrency_code, tc.fullname as tcurrency_fullname, tc.sign as tcurrency_sign " +
                            "FROM ExchangeRates er JOIN Currencies bc ON er.basecurrencyid = bc.id JOIN Currencies tc ON er.targetcurrencyid = tc.id " +
                            "WHERE bc.code = ? AND tc.code = ?",
                    new Object[]{baseCurrencyCode, targetCurrencyCode},
                    new ExchangeRowMapperImpl()).stream().findAny();

        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        return exchangeEntity;
    }

    public ExchangeEntity updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, double rate) {
        ExchangeEntity exchangeEntity = findExchangeByTwoCodes(baseCurrencyCode, targetCurrencyCode).get();
        try {
            jdbcTemplate.update("UPDATE exchangerates SET rate = ? WHERE id = ?", rate, exchangeEntity.getId());
        } catch (DataAccessException ex) {
            throw new InternalException("Database");
        }

        exchangeEntity = getById(exchangeEntity.getId());
        return exchangeEntity;
    }
}

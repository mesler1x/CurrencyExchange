package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.repository.mapper.CurrencyMapper;
import com.edu.mesler.currency.adaper.repository.mapper.CurrencyRowMapperImpl;
import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.exception.AlreadyExistException;
import com.edu.mesler.currency.adaper.web.exception.InternalException;
import com.edu.mesler.currency.adaper.web.exception.NotFoundException;
import com.edu.mesler.currency.domain.CurrencyEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CurrencyRepository {
    JdbcTemplate jdbcTemplate;
    CurrencyMapper currencyMapper;

    public List<CurrencyEntity> getAll() {
        List<CurrencyEntity> queryResult;

        try {
            queryResult = jdbcTemplate.query("SELECT * FROM Currencies", new CurrencyRowMapperImpl());
        } catch (DataAccessException exception) {
            throw new InternalException("Database");
        }

        return queryResult;
    }


    public CurrencyEntity getOneByCode(String code) {
        CurrencyEntity queryResult = null;
        try {
            queryResult = jdbcTemplate.query("SELECT * FROM Currencies WHERE code = ?",
                    new Object[]{code},
                    new CurrencyRowMapperImpl()).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            throw new InternalException("Database");
        }

        if(queryResult == null) {
            throw new NotFoundException("Currency");
        }

        return queryResult;
    }

    public CurrencyEntity create(CurrencyRequest currencyRequest) {
        try {
            jdbcTemplate.update("INSERT INTO Currencies (code, fullName, sign) VALUES (?,?,?)",
                    currencyRequest.code(), currencyRequest.name(), currencyRequest.sign());
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistException("Currency with this code");
        }


        return getOneByCode(currencyRequest.code());
    }

    public CurrencyEntity getOneById(int id) {
        CurrencyEntity currencyEntity;
        try {
            currencyEntity = jdbcTemplate.query("SELECT * FROM Currencies WHERE id = ?", new Object[]{id}, new CurrencyRowMapperImpl()).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            throw new InternalException("Database");
        }

        if (currencyEntity == null) {
            throw new NotFoundException("Currency with id " + id);
        }
        return currencyEntity;
    }
}

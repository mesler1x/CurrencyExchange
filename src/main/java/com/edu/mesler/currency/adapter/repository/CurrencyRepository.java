package com.edu.mesler.currency.adapter.repository;

import com.edu.mesler.currency.adapter.web.dto.request.CurrencyRequest;
import com.edu.mesler.currency.adapter.web.exception.AlreadyExistException;
import com.edu.mesler.currency.adapter.web.exception.InternalException;
import com.edu.mesler.currency.adapter.web.exception.NotFoundException;
import com.edu.mesler.currency.domain.CurrencyEntity;
import com.edu.mesler.currency.service.mapper.CurrencyRowMapperImpl;
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

    public List<CurrencyEntity> getAll() {
        List<CurrencyEntity> queryResult;

        try {
            queryResult = jdbcTemplate.query("SELECT * FROM Currencies", new CurrencyRowMapperImpl());
        } catch (DataAccessException exception) {
            throw new InternalException("Database error occurred while fetching all currencies.");
        }

        return queryResult;
    }


    public CurrencyEntity getOneByCode(String code) {
        CurrencyEntity queryResult;
        try {
            queryResult = jdbcTemplate.query("SELECT * FROM Currencies WHERE code = ?",
                    new Object[]{code},
                    new CurrencyRowMapperImpl()).stream().findFirst().orElseThrow(() -> new NotFoundException("Currency"));
        } catch (DataAccessException e) {
            throw new InternalException("Database error occurred while getting one currency by code");
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
}

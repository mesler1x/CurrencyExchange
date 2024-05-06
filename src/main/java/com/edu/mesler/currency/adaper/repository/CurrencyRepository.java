package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.repository.mapper.CurrencyMapper;
import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import com.edu.mesler.currency.adaper.web.exception.CurrencyNotFoundException;
import com.edu.mesler.currency.adaper.web.exception.DBException;
import com.edu.mesler.currency.adaper.web.exception.NotUniqueException;
import com.edu.mesler.currency.domain.Currency;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CurrencyRepository {
    static Connection connection;
    private final JdbcTemplate jdbcTemplate;

    public List<CurrencyResponse> getAll() {
        List<CurrencyResponse> response = new ArrayList<>();
        List<Currency> queryResult = jdbcTemplate.query("SELECt * FROM Currencies", new CurrencyMapper());
        for(Currency currency : queryResult) {
            CurrencyResponse currencyResponse = new CurrencyResponse(
                    currency.getId(),
                    currency.getFullName(),
                    currency.getCode(),
                    currency.getSign()
            );
            response.add(currencyResponse);
        }
        return response;
    }


    public CurrencyResponse getOneByCode(String code) {
        Currency queryResult = null;
        try {
            queryResult = jdbcTemplate.query("SELECT * FROM Currencies WHERE code = ?",
                    new Object[]{code},
                    new CurrencyMapper()).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            throw new DBException();
        }

        if(queryResult == null) {
            throw new CurrencyNotFoundException();
        }

        return new CurrencyResponse(queryResult.getId(),
                queryResult.getFullName(),
                queryResult.getCode(),
                queryResult.getSign());
    }

    public CurrencyResponse create(CurrencyRequest currencyRequest) {
        try {
            jdbcTemplate.update("INSERT INTO Currencies (code, fullName, sign) VALUES (?,?,?)",
                    currencyRequest.code(), currencyRequest.name(), currencyRequest.sign());
        } catch (DuplicateKeyException e) {
            throw new NotUniqueException();
        }


        return getOneByCode(currencyRequest.code());
    }
}

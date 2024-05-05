package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.repository.mapper.CurrencyMapper;
import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import com.edu.mesler.currency.adaper.web.exception.CurrencyNotFoundException;
import com.edu.mesler.currency.adaper.web.exception.DBException;
import com.edu.mesler.currency.domain.Currency;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
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

    @SneakyThrows
    public CurrencyResponse getOneByCode(String code) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Currencies WHERE code = ?");
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fullName");
                String sign = resultSet.getString("sign");
                return new CurrencyResponse(id, code, fullName, sign);
            }
        } catch (SQLException e) {
            throw new DBException();
        }

        throw new CurrencyNotFoundException();
    }

    @SneakyThrows
    public CurrencyResponse create(CurrencyRequest currencyRequest) {
        CurrencyResponse currencyResponse;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Currencies(code, fullName, sign) VALUES (?,?,?)");

            preparedStatement.setString(1, currencyRequest.code());
            preparedStatement.setString(2, currencyRequest.name());
            preparedStatement.setString(3, currencyRequest.sign());
            preparedStatement.executeUpdate();

            currencyResponse = getOneByCode(currencyRequest.code());


        } catch (SQLException e) {
            throw new DBException();
        }
        return currencyResponse;
    }
}

package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.web.dto.CurrencyRequest;
import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import com.edu.mesler.currency.adaper.web.exception.CurrencyNotFoundException;
import com.edu.mesler.currency.adaper.web.exception.DBException;
import com.edu.mesler.currency.adaper.web.exception.NotUniqueException;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyRepository {
    static Connection connection;
    /*@Value("${spring.datasource.url}")
    static String URL;
    @Value("{spring.datasource.username}")
    static String USERNAME;
    @Value("{spring.datasource.password}")
    static String PASSWORD;*/

    private static final String URL = "jdbc:postgresql://localhost:5433/currency";
    private static final String PASSWORD = "Mama_230506";
    private static final String USERNAME = "postgres";


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @SneakyThrows
    public List<CurrencyResponse> getAll() {
        List<CurrencyResponse> response = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Currencies";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String fullName = resultSet.getString("fullName");
                String sign = resultSet.getString("sign");

                response.add(new CurrencyResponse(
                        id, code, fullName, sign
                ));
            }
        } catch (DBException e) {
            throw new DBException();
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

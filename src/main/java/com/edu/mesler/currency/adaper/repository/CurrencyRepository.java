package com.edu.mesler.currency.adaper.repository;

import com.edu.mesler.currency.adaper.web.dto.CurrencyResponse;
import lombok.AccessLevel;
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
    @Value("${spring.datasource.url}")
    static String URL;
    @Value("{spring.datasource.username}")
    static String USERNAME;
    @Value("{spring.datasource.password}")
    static String PASSWORD;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}

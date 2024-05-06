package com.edu.mesler.currency.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DBProperties {
    String url;
    String username;
    String password;
    String driverClassName;
}

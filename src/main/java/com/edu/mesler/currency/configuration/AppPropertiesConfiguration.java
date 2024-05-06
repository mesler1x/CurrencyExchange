package com.edu.mesler.currency.configuration;

import com.edu.mesler.currency.configuration.properties.DBProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DBProperties.class)
public class AppPropertiesConfiguration {
}

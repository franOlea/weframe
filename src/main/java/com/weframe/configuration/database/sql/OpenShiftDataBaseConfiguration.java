package com.weframe.configuration.database.sql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SuppressWarnings("unused")
@Configuration
@Profile({"openshift", "aws"})
public class OpenShiftDataBaseConfiguration {

    @Value("${database.url}")
    private String url;

    @Value("${database.user}")
    private String user;

    @Value("${database.password}")
    private String password;

    @Value("${database.driver.classname}")
    private String driverClassName;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource =  new DriverManagerDataSource(url, user, password);
        dataSource.setDriverClassName(driverClassName);

        return dataSource;
    }
}

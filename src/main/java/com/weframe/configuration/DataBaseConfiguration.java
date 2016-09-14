package com.weframe.configuration;

import com.weframe.service.user.impl.UserJdbcTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserJdbcTemplateDao getUserJdbcTemplateDao() {
        return new UserJdbcTemplateDao(jdbcTemplate);
    }
}
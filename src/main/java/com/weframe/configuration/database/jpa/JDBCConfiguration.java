package com.weframe.configuration.database.jpa;

import com.weframe.service.user.UserService;
import com.weframe.service.user.impl.UserJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Profile("jdbc")
public class JDBCConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserService getUserJdbcTemplateDao() {
        return new UserJdbcTemplate(getJdbcTemplate());
    }
}
package com.weframe.service.user.impl;

import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;

public class UserJdbcTemplate implements UserDao{
    private static final String INSERT_SQL = "INSERT INTO USER " +
            "(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT) VALUES " +
            "(?, ?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    public UserJdbcTemplate(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(User user) {

    }

    public void update(User user) {

    }

    public void delete(User user) {

    }

    public void delete(long id) {

    }

    public User getById(long id) {
        return null;
    }

    public User getByEmail(String email) {
        return null;
    }

    public User getByLogin(String email, String password) {
        return null;
    }

    public Collection<User> getAllWithPagging(int limit, int offset) {
        return null;
    }
}

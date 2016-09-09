package com.weframe.service.user.impl;

import com.weframe.model.user.Role;
import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

public class UserJdbcTemplateDao implements UserDao {

    public static final BeanPropertyRowMapper<User> USERS_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    public static final BeanPropertyRowMapper<Role> ROLES_ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);

    private JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        Validate.notNull(user, "The user cannot be null");

        jdbcTemplate.update(INSERT_QUERY,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPasswordSalt(),
                user.getRole().getId());
    }

    public void update(final User user) {

    }

    public void delete(final User user) {
        Validate.notNull(user, "The user cannot be null");

        jdbcTemplate.update(DELETE_QUERY, user.getId());
    }

    public void delete(final long id) {
        Validate.notNull(id, "The id cannot be null");
        Validate.isTrue(id >= 0, "The id cannot be a negative number");

        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public User getById(final long id) {
        Validate.notNull(id, "The id cannot be null");
        Validate.isTrue(id >= 0, "The id cannot be a negative number");

        User user = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[] { id }, USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_USER_ID, new Object[] { user.getId() }, ROLES_ROW_MAPPER);
        user.setRole(role);

        return user;
    }

    public User getByEmail(final String email) {
        Validate.notBlank(email, "The email cannot be blank");

        return (User) jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, new Object[] { email }, USERS_ROW_MAPPER);
    }

    public User getByLogin(final String email, final String password) {
        Validate.notBlank(email, "The email cannot be blank");
        Validate.notBlank(password, "The password cannot be blank");

        return (User) jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, new Object[] { email, password }, USERS_ROW_MAPPER);
    }

    public Collection<User> getAll(final int pageNo, final int pageSize) {
        Validate.isTrue(pageNo > 0, "The page number should be above 0");
        Validate.isTrue(pageSize > 0, "The page size should be aboce 0");

        return null;
    }

    public static final String INSERT_QUERY = "INSERT INTO USERS " +
            "(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?)";
    public static final String  DELETE_QUERY = "DELETE FROM USERS WHERE ID = ?";
    public static final String SELECT_BY_ID_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.ID = ? ";
    public static final String SELECT_BY_EMAIL_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.EMAIL = ? ";

    public static final String LOGIN_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.EMAIL = ? AND USERS.PASSWORD = ?";
    public static final String SELECT_ALL_WITH_PAGING = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS ";
    public static final String SELECT_ROLE_BY_USER_ID = "SELECT " +
            "ROLES.ID, " +
            "ROLES.NAME " +
            "FROM ROLES " +
            "INNER JOIN USERS ON ROLES.ID = USERS.ROLE " +
            "WHERE USERS.ID = ?";
}

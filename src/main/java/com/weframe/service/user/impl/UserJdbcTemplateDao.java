package com.weframe.service.user.impl;

import com.weframe.model.user.Role;
import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.List;

public class UserJdbcTemplateDao implements UserDao {

    public static final BeanPropertyRowMapper<User> USERS_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    public static final BeanPropertyRowMapper<Role> ROLES_ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);

    private JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        Validate.notNull(user, "The user cannot be null");
        if(!isValidUser(user)) {
            throw new InvalidUserPersistenceRequestException();
        }

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
        Validate.notNull(user, "The user cannot be null");
        if(user.getFirstName() == null || user.getLastName() == null) {
            throw new InvalidUserPersistenceRequestException();
        }


        jdbcTemplate.update(UPDATE_BY_ID,
                user.getFirstName(),
                user.getLastName(),
                user.getId());

    }

    public void delete(final Long id) {
        Validate.notNull(id, "The id cannot be null");
        if(id < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public User getById(final Long id) {
        Validate.notNull(id, "The id cannot be null");
        if(id < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        User user = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[] { id }, USERS_ROW_MAPPER);
        Role role = getUserRole(id);
        user.setRole(role);

        return user;
    }

    public User getByEmail(final String email) {
        Validate.notBlank(email, "The email cannot be blank");

        User user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, new Object[] { email }, USERS_ROW_MAPPER);
        Role role = getUserRole(user.getId());
        user.setRole(role);

        return user;
    }

    public User getByLogin(final String email, final String password) {
        Validate.notBlank(email, "The email cannot be blank");
        Validate.notBlank(password, "The password cannot be blank");

        User user = jdbcTemplate.queryForObject(LOGIN_QUERY, new Object[] { email, password }, USERS_ROW_MAPPER);
        Role role = getUserRole(user.getId());
        user.setRole(role);

        return user;
    }

    public Collection<User> getAll(final int offset, final int limit) {
        if(offset < 1 || limit < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        List<User> users = jdbcTemplate.query(SELECT_ALL_WITH_PAGING,
                new Object[] { offset, limit }, USERS_ROW_MAPPER);

        users.forEach((user) -> {
            user.setRole(getUserRole(user.getId()));
        });

        return users;
    }

    private Role getUserRole(Long id) {
        return jdbcTemplate.queryForObject(SELECT_ROLE_BY_USER_ID, new Object[] { id }, ROLES_ROW_MAPPER);
    }

    private static boolean isValidUser(User user) {
        return user != null &&
                user.getId() > 0 &&
                user.getEmail() != null &&
                user.getFirstName() != null &&
                user.getLastName() != null &&
                user.getPassword() != null &&
                user.getPasswordSalt() != null &&
                user.getRole() != null &&
                user.getRole().getId() > 0 &&
                user.getRole().getName() != null;
    }

    public static final String INSERT_QUERY = "INSERT INTO USERS " +
            "(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?)";
    public static final String  DELETE_QUERY = "DELETE FROM USERS WHERE ID = ?";
    public static final String SELECT_ALL_WITH_PAGING = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "LIMIT ?,?";
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
    public static final String UPDATE_BY_ID = "UPDATE " +
            "USERS " +
            "SET " +
            "FIRST_NAME = ?, " +
            "LAST_NAME = ? " +
            "WHERE ID = ?;";
    public static final String LOGIN_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.EMAIL = ? AND USERS.PASSWORD = ?";
    public static final String SELECT_ROLE_BY_USER_ID = "SELECT " +
            "ROLES.ID, " +
            "ROLES.NAME " +
            "FROM ROLES " +
            "INNER JOIN USERS ON ROLES.ID = USERS.ROLE " +
            "WHERE USERS.ID = ?";
}

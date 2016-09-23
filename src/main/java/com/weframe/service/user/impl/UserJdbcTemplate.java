package com.weframe.service.user.impl;

import com.weframe.model.user.Role;
import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.List;

@Profile("jdbc")
public class UserJdbcTemplate implements UserDao {

    public static final BeanPropertyRowMapper<User> USERS_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    public static final BeanPropertyRowMapper<Role> ROLES_ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);

    private JdbcTemplate jdbcTemplate;

    public UserJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        if(user == null ||
                !isValidUser(user)) {
            throw new InvalidUserPersistenceRequestException();
        }

        if(jdbcTemplate.update(INSERT_QUERY,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPasswordSalt(),
                user.getRole().getId()) < 1) {
            throw new RuntimeException();
        }

    }

    public void update(final User user) {
        if(user == null ||
                StringUtils.isBlank(user.getFirstName()) ||
                StringUtils.isBlank(user.getLastName())) {

            throw new InvalidUserPersistenceRequestException();
        }


        if(jdbcTemplate.update(UPDATE_BY_ID,
                user.getFirstName(),
                user.getLastName(),
                user.getId()) < 1) {
            throw new RuntimeException();
        }

    }

    public void delete(final Long id) {
        if(id == null ||
                id < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        if(jdbcTemplate.update(DELETE_QUERY, id) < 1) {
            throw new RuntimeException();
        }
    }

    public User getById(final Long id) {
        if(id == null ||
                id < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        User user = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[] { id }, USERS_ROW_MAPPER);
        Role role = getUserRole(id);
        user.setRole(role);

        return user;
    }

    public User getByEmail(final String email) {
        if(StringUtils.isBlank(email)) {
            throw new InvalidUserPersistenceRequestException();
        }

        User user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, new Object[] { email }, USERS_ROW_MAPPER);
        Role role = getUserRole(user.getId());
        user.setRole(role);

        return user;
    }

    public User getByLogin(final String email, final String password) {
        if(StringUtils.isBlank(email) ||
                StringUtils.isBlank(password)) {
            throw new InvalidUserPersistenceRequestException();
        }

        User user = jdbcTemplate.queryForObject(LOGIN_QUERY, new Object[] { email, password }, USERS_ROW_MAPPER);
        Role role = getUserRole(user.getId());
        user.setRole(role);

        return user;
    }

    public Collection<User> getAllWithPaging(final int offset, final int limit) {
        if(offset < 0 || limit < 1) {
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
        return user.getId() > 0 &&
                user.getEmail() != null &&
                user.getFirstName() != null &&
                user.getLastName() != null &&
                user.getPassword() != null &&
                user.getPasswordSalt() != null &&
                user.getRole() != null &&
                user.getRole().getId() > 0 &&
                user.getRole().getName() != null;
    }

    static final String INSERT_QUERY = "INSERT INTO USERS " +
            "(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?)";
    static final String  DELETE_QUERY = "DELETE FROM USERS WHERE ID = ?";
    static final String SELECT_ALL_WITH_PAGING = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "LIMIT ?,?";
    static final String SELECT_BY_ID_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.ID = ? ";
    static final String SELECT_BY_EMAIL_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.EMAIL = ? ";
    static final String UPDATE_BY_ID = "UPDATE " +
            "USERS " +
            "SET " +
            "FIRST_NAME = ?, " +
            "LAST_NAME = ? " +
            "WHERE ID = ?;";
    static final String LOGIN_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD, " +
            "USERS.PASSWORD_SALT " +
            "FROM USERS " +
            "WHERE USERS.EMAIL = ? AND USERS.PASSWORD = ?";
    static final String SELECT_ROLE_BY_USER_ID = "SELECT " +
            "ROLES.ID, " +
            "ROLES.NAME " +
            "FROM ROLES " +
            "INNER JOIN USERS ON ROLES.ID = USERS.ROLE " +
            "WHERE USERS.ID = ?";
}

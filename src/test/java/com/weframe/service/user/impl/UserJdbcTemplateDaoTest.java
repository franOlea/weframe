package com.weframe.service.user.impl;


import com.weframe.configuration.DataBaseConfiguration;
import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.model.user.Role;
import com.weframe.model.user.User;
import com.weframe.model.user.fixture.UserFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("embedded")
@ContextConfiguration(classes={EmbeddedDatabaseConfiguration.class, DataBaseConfiguration.class}, loader=AnnotationConfigContextLoader.class)
public class UserJdbcTemplateDaoTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserJdbcTemplateDao userDao;

    @Before
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE USERS IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE USERS (" +
                "ID SERIAL, " +
                "FIRST_NAME VARCHAR(255), " +
                "LAST_NAME VARCHAR(255), " +
                "EMAIL VARCHAR(255), " +
                "PASSWORD VARCHAR(255), " +
                "PASSWORD_SALT VARCHAR(255), " +
                "ROLE INT)");

        jdbcTemplate.execute("DROP TABLE ROLES IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE ROLES (" +
                "ID SERIAL, " +
                "NAME VARCHAR(255))");

        jdbcTemplate.update("INSERT INTO ROLES (ID, NAME)  VALUES (1, 'USER')");
        jdbcTemplate.update("INSERT INTO ROLES (ID, NAME)  VALUES (2, 'ADMIN')");

        userDao = new UserJdbcTemplateDao(jdbcTemplate);
    }

    @Test
    public void testInsert() {
        userDao.insert(UserFixture.johnDoe());

        User user = jdbcTemplate.queryForObject(UserJdbcTemplateDao.SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplateDao.USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(UserJdbcTemplateDao.SELECT_ROLE_BY_USER_ID,
                new Object[] { user.getId() },
                UserJdbcTemplateDao.ROLES_ROW_MAPPER);
        user.setRole(role);


        Assert.assertEquals(user, UserFixture.johnDoe());
    }

    @Test
    public void testSelectById() {
        jdbcTemplate.update(UserJdbcTemplateDao.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User user = userDao.getById(UserFixture.johnDoe().getId());

        Assert.assertEquals(user, UserFixture.johnDoe());
    }

    @Test
    public void testSelectByEmail() {
        jdbcTemplate.update(UserJdbcTemplateDao.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User user = userDao.getByEmail(UserFixture.johnDoe().getEmail());

        Assert.assertEquals(user, UserFixture.johnDoe());
    }

    @Test
    public void testSelectByLogin() {
        jdbcTemplate.update(UserJdbcTemplateDao.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User user = userDao.getByLogin(UserFixture.johnDoe().getEmail(), UserFixture.johnDoe().getPassword());

        Assert.assertEquals(user, UserFixture.johnDoe());
    }

    @Test
    public void testDelete() {
        exception.expect(EmptyResultDataAccessException.class);

        jdbcTemplate.update(UserJdbcTemplateDao.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        userDao.delete(UserFixture.johnDoe().getId());

        jdbcTemplate.queryForObject(UserJdbcTemplateDao.SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplateDao.USERS_ROW_MAPPER);
    }

    @Test
    public void testUpdate() {
        jdbcTemplate.update(UserJdbcTemplateDao.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User updatedUser = UserFixture.johnDoe();
        updatedUser.setEmail("another@email.com");

        userDao.update(updatedUser);

        User user = jdbcTemplate.queryForObject(UserJdbcTemplateDao.SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplateDao.USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(UserJdbcTemplateDao.SELECT_ROLE_BY_USER_ID,
                new Object[] { user.getId() },
                UserJdbcTemplateDao.ROLES_ROW_MAPPER);
        user.setRole(role);

        Assert.assertNotEquals(UserFixture.johnDoe(), updatedUser);
        Assert.assertNotEquals(UserFixture.johnDoe(), user);
        Assert.assertEquals(user, updatedUser);
    }

}
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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("embedded")
@ContextConfiguration(classes={EmbeddedDatabaseConfiguration.class, DataBaseConfiguration.class}, loader=AnnotationConfigContextLoader.class)
public class UserJdbcTemplateDaoTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserJdbcTemplateDao userDao;

    @Before
    public void setUp() {
        jdbcTemplate.execute("SET MODE MySQL");
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

        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (3, 'Quinn', 'Stevenson', " +
                "'at.lacus@venenatisamagna.co.uk', 'WLE96XAN1HL', 'IUL44ERZ1XY', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (4, 'Chase', 'Grimes', " +
                "'Nulla.facilisi@massaMaurisvestibulum.com', 'XGG15SLY2JI', 'LZA60FRP3KB', 2)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (5, 'September', 'Atkins', " +
                "'libero@risus.org', 'ABZ54TRN7FU', 'DTP59DGX8FD', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (6, 'Marah', 'Kirkland', " +
                "'ullamcorper.eu.euismod@hendreritidante.ca', 'UIL71MVD3DB', 'AGA25VVZ8NP', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (7, 'Ursa', 'Hester', " +
                "'metus@ante.ca', 'DBT69CDH2PZ', 'ELS95OWH0ZO', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (8, 'Rebekah', 'Morris', " +
                "'mauris.a.nunc@tinciduntDonec.ca', 'ZBR57MYT5PF', 'WJQ00JPW4QK', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (9, 'Thomas', 'Carney', " +
                "'eleifend.nunc.risus@Sed.net', 'ZIK00DZF8UP', 'FCX15KPZ2SI', 2)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (10, ?, ?, ?, ?, ?, ?)",
                UserFixture.janeDoe().getFirstName(),
                UserFixture.janeDoe().getLastName(),
                UserFixture.janeDoe().getEmail(),
                UserFixture.janeDoe().getPassword(),
                UserFixture.janeDoe().getPasswordSalt(),
                UserFixture.janeDoe().getRole().getId());
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE) " +
                "VALUES (11, 'Murphy', 'Woodard', " +
                "'ipsum.non.arcu@malesuada.co.uk', 'DYM00AEP8KQ', 'GRX00AGH3NC', 1)");
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
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplateDao.ROLES_ROW_MAPPER);
        user.setRole(role);

        Assert.assertNotEquals(UserFixture.johnDoe(), updatedUser);
        Assert.assertNotEquals(UserFixture.johnDoe(), user);
        Assert.assertEquals(user, updatedUser);
    }

    @Test
    public void testGetAll() {
        List<User> users = (List<User>) userDao.getAll(3, 7);
        User fixtureUser = UserFixture.janeDoe();
        fixtureUser.setId(10);

        users.forEach((user) -> {
            if(user.getId() == 10) {
                Assert.assertEquals(user, fixtureUser);
            }
        });
    }

}
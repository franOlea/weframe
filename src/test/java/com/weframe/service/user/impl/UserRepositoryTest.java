package com.weframe.service.user.impl;

import com.weframe.configuration.database.jpa.JDBCConfiguration;
import com.weframe.configuration.database.jpa.ORMRepositoryConfiguration;
import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.model.user.User;
import com.weframe.model.user.fixture.UserFixture;
import com.weframe.service.user.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"embedded", "jdbc", "orm"})
@ContextConfiguration(
        classes = {EmbeddedDatabaseConfiguration.class,
                JDBCConfiguration.class,
                ORMRepositoryConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@EnableJpaRepositories
public class UserRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = userRepository;
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
    public void getById() {
        User user = userDao.getById(10L);

        Assert.assertEquals(UserFixture.janeDoe().getEmail(), user.getEmail());
        Assert.assertEquals(UserFixture.janeDoe().getFirstName(), user.getFirstName());
        Assert.assertEquals(UserFixture.janeDoe().getLastName(), user.getLastName());
        Assert.assertEquals(UserFixture.janeDoe().getPassword(), user.getPassword());
        Assert.assertEquals(UserFixture.janeDoe().getPasswordSalt(), user.getPasswordSalt());
        Assert.assertEquals(UserFixture.janeDoe().getRole(), user.getRole());
    }

}

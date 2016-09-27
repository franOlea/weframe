package com.weframe.service.user.impl;

import com.weframe.configuration.database.jpa.JDBCConfiguration;
import com.weframe.configuration.database.jpa.ORMRepositoryConfiguration;
import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.model.user.Role;
import com.weframe.model.user.User;
import com.weframe.model.user.fixture.UserFixture;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"embedded", "jdbc", "orm"})
@ContextConfiguration(
        classes = {EmbeddedDatabaseConfiguration.class,
                JDBCConfiguration.class,
                ORMRepositoryConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@EnableJpaRepositories
public class UserRepositoryTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userDao;

    private static boolean setUpIsDone = false;

    @Test
    public void insert() {
        userDao.insert(UserFixture.johnDoe());

        User user = jdbcTemplate.queryForObject(UserJdbcTemplate.SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplate.USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(UserJdbcTemplate.SELECT_ROLE_BY_USER_ID,
                new Object[] { user.getId() },
                UserJdbcTemplate.ROLES_ROW_MAPPER);
        user.setRole(role);

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void insertInvalidUser() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        User user = UserFixture.johnDoe();
        user.setFirstName(null);
        userDao.insert(user);
    }

    @Test
    public void insertNullUser() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.insert(null);
    }

    @Test
    public void getById() {
        jdbcTemplate.update(UserJdbcTemplate.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User user = userDao.getById(UserFixture.johnDoe().getId());

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void getByIdWithInvalidId() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getById(0L);
    }

    @Test
    public void getByIdWithNullId() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getById(null);
    }

    @Test
    public void getByEmail() {
        jdbcTemplate.update(UserJdbcTemplate.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());
        User user = userDao.getByEmail(UserFixture.johnDoe().getEmail());

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void getByEmailWithBlankEmail() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getByEmail("");
    }

    @Test
    public void getByEmailWithNullEmail() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getByEmail(null);
    }

    @Test
    public void getByLogin() {
        jdbcTemplate.update(UserJdbcTemplate.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User user = userDao.getByLogin(UserFixture.johnDoe().getEmail(), UserFixture.johnDoe().getPassword());

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void getByLoginWithBlankEmail() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getByLogin("", "123");
    }

    @Test
    public void getByLoginWithNullEmail() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getByLogin(null, "123");
    }

    @Test
    public void getByLoginWithBlankPassword() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getByLogin("123", "");
    }

    @Test
    public void getByLoginWithNullPassword() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getByLogin("123", null);
    }

    @Test
    public void delete() {
        exception.expect(EmptyResultDataAccessException.class);

        jdbcTemplate.update(UserJdbcTemplate.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        userDao.deleteById(UserFixture.johnDoe().getId());

        jdbcTemplate.queryForObject(UserJdbcTemplate.SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplate.USERS_ROW_MAPPER);

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void deleteWithInvalidId() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.deleteById(0L);
    }

    @Test
    public void deleteWithNullId() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.deleteById(null);
    }

    @Test
    public void update() {
        jdbcTemplate.update(UserJdbcTemplate.INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getPasswordSalt(),
                UserFixture.johnDoe().getRole().getId());

        User updatedUser = UserFixture.johnDoe();
        updatedUser.setFirstName("Juan");

        userDao.update(updatedUser);

        User user = jdbcTemplate.queryForObject(UserJdbcTemplate.SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplate.USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(UserJdbcTemplate.SELECT_ROLE_BY_USER_ID,
                new Object[] { UserFixture.johnDoe().getId() },
                UserJdbcTemplate.ROLES_ROW_MAPPER);
        user.setRole(role);

        Assert.assertNotEquals(UserFixture.johnDoe(), updatedUser);
        Assert.assertNotEquals(UserFixture.johnDoe(), user);
        Assert.assertEquals(user, updatedUser);

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void updateWithNullUser() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.update(null);
    }

    @Test
    public void updateWithNullFirstName() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        User user = UserFixture.johnDoe();
        user.setFirstName(null);
        userDao.update(user);
    }

    @Test
    public void updateWithNullLastName() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        User user = UserFixture.johnDoe();
        user.setLastName(null);
        userDao.update(user);
    }

    @Test
    public void getAll() {
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

        List<User> users = (List<User>) userDao.getAllWithPaging(3, 7);
        User fixtureUser = UserFixture.janeDoe();
        fixtureUser.setId(10L);

        users.forEach((user) -> {
            if(user.getId() == 10L) {
                Assert.assertEquals(user, fixtureUser);
            }
        });

        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 3);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 4);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 5);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 6);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 7);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 8);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 9);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 10);
        jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY, 11);
    }

    @Test
    public void getAllWithInvalidLimit() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getAllWithPaging(1, 0);
    }

    @Test
    public void getAllWithInvalidOffset() {
        exception.expect(InvalidUserPersistenceRequestException.class);
        userDao.getAllWithPaging(-1, 1);
    }

}

package com.weframe.user.service.impl;

import com.weframe.configuration.database.jpa.JDBCConfiguration;
import com.weframe.configuration.database.jpa.ORMRepositoryConfiguration;
import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.user.model.Role;
import com.weframe.user.model.User;
import com.weframe.user.model.fixture.UserFixture;
import com.weframe.user.service.exception.InvalidUserPersistenceRequestException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    private static boolean setUpIsDone = false;

    @Test
    public void insert() {
        userRepository.insert(UserFixture.johnDoe());

        User user = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_USER_ID,
                new Object[] { user.getId() },
                ROLES_ROW_MAPPER);
        user.setRole(role);

        User expected = UserFixture.johnDoe();
        expected.setPassword(user.getPassword());

        Assert.assertEquals(expected, user);

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void insertInvalidUser() {
        User user = UserFixture.johnDoe();
        user.setFirstName(null);
        userRepository.insert(user);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void insertNullUser() {
        userRepository.insert(null);
    }

    @Test
    public void getById() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getRole().getId());

        User user = userRepository.getById(UserFixture.johnDoe().getId());

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByIdWithInvalidId() {
        userRepository.getById(0L);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByIdWithNullId() {
        userRepository.getById(null);
    }

    @Test
    public void getByEmail() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getRole().getId());
        User user = userRepository.getByEmail(UserFixture.johnDoe().getEmail());

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByEmailWithBlankEmail() {
        userRepository.getByEmail("");
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByEmailWithNullEmail() {
        userRepository.getByEmail(null);
    }

    @Test
    public void getByLogin() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getRole().getId());

        User user = userRepository.getByLogin(UserFixture.johnDoe().getEmail(), UserFixture.johnDoe().getPassword());

        Assert.assertEquals(user, UserFixture.johnDoe());

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByLoginWithBlankEmail() {
        userRepository.getByLogin("", "123");
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByLoginWithNullEmail() {
        userRepository.getByLogin(null, "123");
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByLoginWithBlankPassword() {
        userRepository.getByLogin("123", "");
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getByLoginWithNullPassword() {
        userRepository.getByLogin("123", null);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getRole().getId());

        userRepository.deleteById(UserFixture.johnDoe().getId());

        jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                USERS_ROW_MAPPER);

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void deleteWithInvalidId() {
        userRepository.deleteById(0L);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void deleteWithNullId() {
        userRepository.deleteById(null);
    }

    @Test
    public void update() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                UserFixture.johnDoe().getPassword(),
                UserFixture.johnDoe().getRole().getId());

        User updatedUser = UserFixture.johnDoe();
        updatedUser.setFirstName("Juan");

        userRepository.update(updatedUser);

        User user = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_USER_ID,
                new Object[] { UserFixture.johnDoe().getId() },
                ROLES_ROW_MAPPER);
        user.setRole(role);

        Assert.assertNotEquals(UserFixture.johnDoe(), updatedUser);
        Assert.assertNotEquals(UserFixture.johnDoe(), user);
        Assert.assertEquals(user, updatedUser);

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void updateWithNullUser() {
        userRepository.update(null);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void updateWithNullFirstName() {
        User user = UserFixture.johnDoe();
        user.setFirstName(null);
        userRepository.update(user);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void updateWithNullLastName() {
        User user = UserFixture.johnDoe();
        user.setLastName(null);
        userRepository.update(user);
    }

    @Test
    public void getAll() {
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (3, 'Quinn', 'Stevenson', " +
                "'at.lacus@venenatisamagna.co.uk', 'WLE96XAN1HL', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (4, 'Chase', 'Grimes', " +
                "'Nulla.facilisi@massaMaurisvestibulum.com', 'LZA60FRP3KB', 2)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (5, 'September', 'Atkins', " +
                "'libero@risus.org', 'ABZ54TRN7FU', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (6, 'Marah', 'Kirkland', " +
                "'ullamcorper.eu.euismod@hendreritidante.ca', 'UIL71MVD3DB', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (7, 'Ursa', 'Hester', " +
                "'metus@ante.ca', 'DBT69CDH2PZ', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (8, 'Rebekah', 'Morris', " +
                "'mauris.a.nunc@tinciduntDonec.ca', 'ZBR57MYT5PF', 1)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (9, 'Thomas', 'Carney', " +
                "'eleifend.nunc.risus@Sed.net', 'ZIK00DZF8UP', 2)");
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                        "VALUES (10, ?, ?, ?, ?, ?)",
                UserFixture.janeDoe().getFirstName(),
                UserFixture.janeDoe().getLastName(),
                UserFixture.janeDoe().getEmail(),
                UserFixture.janeDoe().getPassword(),
                UserFixture.janeDoe().getRole().getId());
        jdbcTemplate.update("INSERT INTO USERS (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) " +
                "VALUES (11, 'Murphy', 'Woodard', " +
                "'ipsum.non.arcu@malesuada.co.uk', 'DYM00AEP8KQ', 1)");

        List<User> users = (List<User>) userRepository.getAllWithPaging(3, 7);
        User fixtureUser = UserFixture.janeDoe();
        fixtureUser.setId(10L);

        users.forEach((user) -> {
            if(user.getId() == 10L) {
                Assert.assertEquals(user, fixtureUser);
            }
        });

        jdbcTemplate.update(DELETE_QUERY, 3);
        jdbcTemplate.update(DELETE_QUERY, 4);
        jdbcTemplate.update(DELETE_QUERY, 5);
        jdbcTemplate.update(DELETE_QUERY, 6);
        jdbcTemplate.update(DELETE_QUERY, 7);
        jdbcTemplate.update(DELETE_QUERY, 8);
        jdbcTemplate.update(DELETE_QUERY, 9);
        jdbcTemplate.update(DELETE_QUERY, 10);
        jdbcTemplate.update(DELETE_QUERY, 11);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getAllWithInvalidLimit() {
        userRepository.getAllWithPaging(1, 0);
    }

    @Test(expected = InvalidUserPersistenceRequestException.class)
    public void getAllWithInvalidOffset() {
        userRepository.getAllWithPaging(-1, 1);
    }

    @Test
    public void changePasswordWithValidOldPassword() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                userRepository.generateStoringPasswordHash(UserFixture.johnDoe().getPassword()),
                UserFixture.johnDoe().getRole().getId());

        userRepository.changePassword(UserFixture.johnDoe().getPassword(), "password", UserFixture.johnDoe().getId());

        User user = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
                new Object[] { UserFixture.johnDoe().getId() },
                USERS_ROW_MAPPER);
        Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_USER_ID,
                new Object[] { UserFixture.johnDoe().getId() },
                ROLES_ROW_MAPPER);
        user.setRole(role);

        Assert.assertTrue(userRepository.isValidPassword("password", user.getPassword()));

        jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId());
    }

    @Test
    public void changePasswordWithInvalidOldPassword() {
        jdbcTemplate.update(INSERT_QUERY,
                UserFixture.johnDoe().getId(),
                UserFixture.johnDoe().getFirstName(),
                UserFixture.johnDoe().getLastName(),
                UserFixture.johnDoe().getEmail(),
                userRepository.generateStoringPasswordHash(UserFixture.johnDoe().getPassword()),
                UserFixture.johnDoe().getRole().getId());

        boolean exceptionThrown = false;
        try {
            userRepository.changePassword("invalidPassword", "invalidPassword", UserFixture.johnDoe().getId());
        } catch(InvalidUserPersistenceRequestException e) {
            exceptionThrown = true;
        }

        Assert.assertTrue(exceptionThrown);
        Assert.assertEquals(1L, jdbcTemplate.update(DELETE_QUERY, UserFixture.johnDoe().getId()));
    }

    private static final String INSERT_QUERY = "INSERT INTO USERS " +
            "(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) VALUES " +
            "(?, ?, ?, ?, ?, ?)";
    private static final String  DELETE_QUERY = "DELETE FROM USERS WHERE ID = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT " +
            "USERS.ID, " +
            "USERS.FIRST_NAME, " +
            "USERS.LAST_NAME, " +
            "USERS.EMAIL, " +
            "USERS.PASSWORD " +
            "FROM USERS " +
            "WHERE USERS.ID = ? ";
    private static final String SELECT_ROLE_BY_USER_ID = "SELECT " +
            "ROLES.ID, " +
            "ROLES.NAME " +
            "FROM ROLES " +
            "INNER JOIN USERS ON ROLES.ID = USERS.ROLE " +
            "WHERE USERS.ID = ?";
    private static final BeanPropertyRowMapper<User> USERS_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    private static final BeanPropertyRowMapper<Role> ROLES_ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);

}

package com.weframe.configuration.database.jpa;


import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.service.user.UserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"embedded", "jdbc"})
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = { EmbeddedDatabaseConfiguration.class, JDBCConfiguration.class })
public class JDBCConfigurationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void testJdbcTemplateNotNull() {
        Assert.assertNotNull(jdbcTemplate);
    }

    @Test
    public void testUserDaoNotNull() {
        Assert.assertNotNull(userService);
    }

}

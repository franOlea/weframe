package com.weframe.configuration.database.jpa;


import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"embedded", "jdbc"})
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = { EmbeddedDatabaseConfiguration.class, JDBCConfiguration.class })
public class JDBCConfigurationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testJdbcTemplateNotNull() {
        Assert.assertNotNull(jdbcTemplate);
    }

}

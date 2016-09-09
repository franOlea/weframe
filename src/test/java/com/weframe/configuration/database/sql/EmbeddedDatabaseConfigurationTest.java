package com.weframe.configuration.database.sql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("embedded")
@ContextConfiguration(classes=EmbeddedDatabaseConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class EmbeddedDatabaseConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testContext() {

        new JdbcTemplate(dataSource);

    }

}

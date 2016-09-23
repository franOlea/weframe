package com.weframe.configuration.database.sql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("openshift")
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = OpenShiftDataBaseConfiguration.class)
@TestPropertySource("classpath:/application-openshift.properties")
public class OpenShiftDatabaseConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSourceNotNull() {
        Assert.assertNotNull(dataSource);
    }

}

package com.weframe.configuration.database.jpa;

import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"embedded", "orm"})
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = { EmbeddedDatabaseConfiguration.class, ORMRepositoryConfiguration.class })
public class ORMRepositoryConfigurationTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Test
    public void testEntityManagerFactoryNotNull() {
        Assert.assertNotNull(entityManagerFactory);
    }

    @Test
    public void testPlatformTransactionManagerNotNull() {
        Assert.assertNotNull(platformTransactionManager);
    }

}

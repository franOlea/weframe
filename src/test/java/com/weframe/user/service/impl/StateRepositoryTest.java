package com.weframe.user.service.impl;

import com.weframe.configuration.database.jpa.JDBCConfiguration;
import com.weframe.configuration.database.jpa.ORMRepositoryConfiguration;
import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.user.model.State;
import org.junit.Assert;
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
public class StateRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StateRepository stateRepository;

    @Test
    public void getDefaultState() {
        Assert.assertEquals(stateRepository.getDefaultState(), new State(1, "PENDING_ACTIVATION"));
    }

    @Test
    public void getByName() {
        Assert.assertEquals(stateRepository.getByName("ACTIVE"), new State(2, "ACTIVE"));
    }

    @Test
    public void getById() {
        Assert.assertEquals(stateRepository.getById(1L), new State(1, "PENDING_ACTIVATION"));
    }
}

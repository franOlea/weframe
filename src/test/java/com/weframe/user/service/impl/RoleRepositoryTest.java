package com.weframe.user.service.impl;

import com.weframe.configuration.database.jpa.JDBCConfiguration;
import com.weframe.configuration.database.jpa.ORMRepositoryConfiguration;
import com.weframe.configuration.database.sql.EmbeddedDatabaseConfiguration;
import com.weframe.user.model.Role;
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
public class RoleRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getDefaultRole() {
        Assert.assertEquals(roleRepository.getDefaultRole(), new Role(1, "USER"));
    }

    @Test
    public void getByName() {
        Assert.assertEquals(roleRepository.getByName("USER"), new Role(1, "USER"));
    }

    @Test
    public void getById() {
        Assert.assertEquals(roleRepository.getById(1L), new Role(1, "USER"));
    }
}

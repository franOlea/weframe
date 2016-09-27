package com.weframe.service.user.impl;

import com.weframe.model.user.User;
import com.weframe.model.user.fixture.UserFixture;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(JMockit.class)
public class UserJdbcTemplateDaoMockedTemplateTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Injectable
    private JdbcTemplate jdbcTemplate;

    @Tested
    private UserJdbcTemplate userDao = new UserJdbcTemplate(jdbcTemplate);

    @Test
    public void insertError() {
        exception.expect(RuntimeException.class);

        User user = UserFixture.johnDoe();
        new Expectations() {{
            jdbcTemplate.update(UserJdbcTemplate.INSERT_QUERY,
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPasswordSalt(),
                    user.getRole().getId());
            result = 0;
            times = 1;
        }};

        userDao.insert(user);
    }

    @Test
    public void updateError() {
        exception.expect(RuntimeException.class);

        User user = UserFixture.johnDoe();
        new Expectations() {{
            jdbcTemplate.update(UserJdbcTemplate.UPDATE_BY_ID,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getId());
            result = 0;
            times = 1;
        }};

        userDao.update(user);
    }

    @Test
    public void deleteError() {
        exception.expect(RuntimeException.class);

        User user = UserFixture.johnDoe();
        new Expectations() {{
            jdbcTemplate.update(UserJdbcTemplate.DELETE_QUERY,
                    user.getId());
            result = 0;
            times = 1;
        }};

        userDao.deleteById(user.getId());
    }
}

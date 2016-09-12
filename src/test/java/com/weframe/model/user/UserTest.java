package com.weframe.model.user;

import com.weframe.model.user.fixture.UserFixture;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_ID;
import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_FIRST_NAME;
import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_LAST_NAME;
import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_EMAIL;
import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_PASSWORD;
import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_PASSWORD_SALT;
import static com.weframe.model.user.fixture.UserFixture.JOHN_DOE_ROLE;

public class UserTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void createUser() {
        User user = new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
        Assert.assertEquals(user.getId(), JOHN_DOE_ID);
        Assert.assertEquals(user.getFirstName(), JOHN_DOE_FIRST_NAME);
        Assert.assertEquals(user.getLastName(), JOHN_DOE_LAST_NAME);
        Assert.assertEquals(user.getEmail(), JOHN_DOE_EMAIL);
        Assert.assertEquals(user.getPassword(), JOHN_DOE_PASSWORD);
        Assert.assertEquals(user.getPasswordSalt(), JOHN_DOE_PASSWORD_SALT);
        Assert.assertEquals(user.getRole(), JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNegativeId() {
        exception.expect(IllegalArgumentException.class);

        new User(-1, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNullFirstName() {
        exception.expect(NullPointerException.class);

        new User(JOHN_DOE_ID, null, JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithEmptyFirstName() {
        exception.expect(IllegalArgumentException.class);

        new User(JOHN_DOE_ID, "", JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNullLastName() {
        exception.expect(NullPointerException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, null, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithEmptyLastName() {
        exception.expect(IllegalArgumentException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, "", JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNullEmail() {
        exception.expect(NullPointerException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, null, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithEmptyEmail() {
        exception.expect(IllegalArgumentException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, "", JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNullPassword() {
        exception.expect(NullPointerException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, null, JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithEmptyPassword() {
        exception.expect(IllegalArgumentException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_PASSWORD, "", JOHN_DOE_PASSWORD_SALT, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNullPasswordSalt() {
        exception.expect(NullPointerException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, null, JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithEmptyPasswordSalt() {
        exception.expect(IllegalArgumentException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD, "", JOHN_DOE_ROLE);
    }

    @Test
    public void createUserWithNullRole() {
        exception.expect(NullPointerException.class);

        new User(JOHN_DOE_ID, JOHN_DOE_FIRST_NAME, JOHN_DOE_LAST_NAME, JOHN_DOE_EMAIL, JOHN_DOE_PASSWORD, JOHN_DOE_PASSWORD_SALT, null);
    }

    @Test
    public void UserEquals() {
        Assert.assertEquals(UserFixture.johnDoe(),
                new User(UserFixture.JOHN_DOE_ID,
                        UserFixture.JOHN_DOE_FIRST_NAME,
                        UserFixture.JOHN_DOE_LAST_NAME,
                        UserFixture.JOHN_DOE_EMAIL,
                        UserFixture.JOHN_DOE_PASSWORD,
                        UserFixture.JOHN_DOE_PASSWORD_SALT,
                        UserFixture.JOHN_DOE_ROLE));
    }

    @Test
    public void UserToString() {
        Assert.assertEquals(UserFixture.johnDoe().toString(),
                new User(UserFixture.JOHN_DOE_ID,
                        UserFixture.JOHN_DOE_FIRST_NAME,
                        UserFixture.JOHN_DOE_LAST_NAME,
                        UserFixture.JOHN_DOE_EMAIL,
                        UserFixture.JOHN_DOE_PASSWORD,
                        UserFixture.JOHN_DOE_PASSWORD_SALT,
                        UserFixture.JOHN_DOE_ROLE).toString());
    }

    @Test
    public void UserHashCode() {
        Assert.assertEquals(UserFixture.johnDoe().hashCode(),
                new User(UserFixture.JOHN_DOE_ID,
                        UserFixture.JOHN_DOE_FIRST_NAME,
                        UserFixture.JOHN_DOE_LAST_NAME,
                        UserFixture.JOHN_DOE_EMAIL,
                        UserFixture.JOHN_DOE_PASSWORD,
                        UserFixture.JOHN_DOE_PASSWORD_SALT,
                        UserFixture.JOHN_DOE_ROLE).hashCode());
    }

}

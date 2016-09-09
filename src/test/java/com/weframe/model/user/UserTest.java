package com.weframe.model.user;

import com.weframe.model.user.fixture.UserFixture;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.weframe.model.user.fixture.UserFixture.ID;
import static com.weframe.model.user.fixture.UserFixture.FIRST_NAME;
import static com.weframe.model.user.fixture.UserFixture.LAST_NAME;
import static com.weframe.model.user.fixture.UserFixture.EMAIL;
import static com.weframe.model.user.fixture.UserFixture.PASSWORD;
import static com.weframe.model.user.fixture.UserFixture.PASSWORD_SALT;
import static com.weframe.model.user.fixture.UserFixture.ROLE;

public class UserTest {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void createUser() {
        User user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
        Assert.assertEquals(user.getId(), ID);
        Assert.assertEquals(user.getFirstName(), FIRST_NAME);
        Assert.assertEquals(user.getLastName(), LAST_NAME);
        Assert.assertEquals(user.getEmail(), EMAIL);
        Assert.assertEquals(user.getPassword(), PASSWORD);
        Assert.assertEquals(user.getPasswordSalt(), PASSWORD_SALT);
        Assert.assertEquals(user.getRole(), ROLE);
    }

    @Test
    public void createUserWithNegativeId() {
        exception.expect(IllegalArgumentException.class);

        new User(-1, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithNullFirstName() {
        exception.expect(NullPointerException.class);

        new User(ID, null, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithEmptyFirstName() {
        exception.expect(IllegalArgumentException.class);

        new User(ID, "", LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithNullLastName() {
        exception.expect(NullPointerException.class);

        new User(ID, FIRST_NAME, null, EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithEmptyLastName() {
        exception.expect(IllegalArgumentException.class);

        new User(ID, FIRST_NAME, "", EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithNullEmail() {
        exception.expect(NullPointerException.class);

        new User(ID, FIRST_NAME, LAST_NAME, null, PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithEmptyEmail() {
        exception.expect(IllegalArgumentException.class);

        new User(ID, FIRST_NAME, LAST_NAME, "", PASSWORD, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithNullPassword() {
        exception.expect(NullPointerException.class);

        new User(ID, FIRST_NAME, LAST_NAME, EMAIL, null, PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithEmptyPassword() {
        exception.expect(IllegalArgumentException.class);

        new User(ID, FIRST_NAME, LAST_NAME, PASSWORD, "", PASSWORD_SALT, ROLE);
    }

    @Test
    public void createUserWithNullPasswordSalt() {
        exception.expect(NullPointerException.class);

        new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, null, ROLE);
    }

    @Test
    public void createUserWithEmptyPasswordSalt() {
        exception.expect(IllegalArgumentException.class);

        new User(ID, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD, "", ROLE);
    }

    @Test
    public void createUserWithNullRole() {
        exception.expect(NullPointerException.class);

        new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, null);
    }

    @Test
    public void UserEquals() {
        Assert.assertEquals(UserFixture.johnDoe(),
                new User(UserFixture.ID,
                        UserFixture.FIRST_NAME,
                        UserFixture.LAST_NAME,
                        UserFixture.EMAIL,
                        UserFixture.PASSWORD,
                        UserFixture.PASSWORD_SALT,
                        UserFixture.ROLE));
    }

    @Test
    public void UserToString() {
        Assert.assertEquals(UserFixture.johnDoe().toString(),
                new User(UserFixture.ID,
                        UserFixture.FIRST_NAME,
                        UserFixture.LAST_NAME,
                        UserFixture.EMAIL,
                        UserFixture.PASSWORD,
                        UserFixture.PASSWORD_SALT,
                        UserFixture.ROLE).toString());
    }

    @Test
    public void UserHashCode() {
        Assert.assertEquals(UserFixture.johnDoe().hashCode(),
                new User(UserFixture.ID,
                        UserFixture.FIRST_NAME,
                        UserFixture.LAST_NAME,
                        UserFixture.EMAIL,
                        UserFixture.PASSWORD,
                        UserFixture.PASSWORD_SALT,
                        UserFixture.ROLE).hashCode());
    }

}

package com.weframe.model.user.fixture;

import com.weframe.model.user.Role;
import com.weframe.model.user.User;

public class UserFixture {

    public static final long ID = 1;
    public static final String FIRST_NAME = "Jhon";
    public static final String LAST_NAME = "Doe";
    public static final String EMAIL = "john.doe@email.com";
    public static final String PASSWORD = "123456";
    public static final String PASSWORD_SALT = FIRST_NAME + LAST_NAME;
    public static final Role ROLE = RoleFixture.user();

    public static User johnDoe() {
        return new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PASSWORD_SALT, ROLE);
    }

}

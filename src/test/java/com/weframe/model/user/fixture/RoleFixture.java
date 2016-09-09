package com.weframe.model.user.fixture;

import com.weframe.model.user.Role;

public class RoleFixture {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static Role user() {
        return new Role(USER_ID, USER);
    }

    public static Role admin() {
        return new Role(ADMIN_ID, ADMIN);
    }

}

package com.weframe.model.user.fixture;

import com.weframe.model.user.Role;

public class RoleFixture {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final Long USER_ID = 1L;
    public static final Long ADMIN_ID = 2L;

    public static Role user() {
        return new Role(USER_ID, USER);
    }

    public static Role admin() {
        return new Role(ADMIN_ID, ADMIN);
    }

}

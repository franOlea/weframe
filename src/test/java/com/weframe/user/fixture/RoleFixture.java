package com.weframe.user.fixture;

import com.weframe.user.model.Role;

public class RoleFixture {

    private static final Role user = new Role(1, "USER");
    private static final Role admin = new Role(2, "ADMIN");

    public static Role getUser() {
        return user;
    }

    public static Role getAdmin() {
        return admin;
    }
}

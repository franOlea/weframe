package com.weframe.user.fixture;

import com.weframe.user.model.State;
import com.weframe.user.model.User;

public class UserFixture {

    private static User defaultPersistedUser;

    public static User getDefaultPersistedUser() {
        if(defaultPersistedUser == null) {
            defaultPersistedUser = new User();
            defaultPersistedUser.setId((long)1);
            defaultPersistedUser.setEmail("john.doe@gmail.com");
            defaultPersistedUser.setPassword("password");
            defaultPersistedUser.setFirstName("John");
            defaultPersistedUser.setLastName("Doe");
            defaultPersistedUser.setRole(RoleFixture.getUser());
            defaultPersistedUser.setState(StateFixture.getActive());
        }
        return defaultPersistedUser;
    }

}

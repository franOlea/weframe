package com.weframe.user.fixture;

import com.weframe.user.model.State;
import com.weframe.user.model.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserFixture {

    private static User defaultPersistedUser;
    private static Collection<User> defaultPersistedUsers;

    public static User getDefaultPersistedUser() {
        if(defaultPersistedUser == null) {
            defaultPersistedUser = new User();
            defaultPersistedUser.setId((long)1);
            defaultPersistedUser.setEmail("john.doe@gmail.com");
            defaultPersistedUser.setPassword("password");
            defaultPersistedUser.setFirstName("John");
            defaultPersistedUser.setLastName("Doe");
            defaultPersistedUser.setRole(RoleFixture.getAdmin());
            defaultPersistedUser.setState(StateFixture.getActive());
        }
        return defaultPersistedUser;
    }

    public static Collection<User> getDefaultPersistedUsers() {
        if(defaultPersistedUsers == null) {
            defaultPersistedUsers = new ArrayList<>();
            defaultPersistedUsers.add(getDefaultPersistedUser());
            User janeDoe = new User();
            janeDoe.setId((long)1);
            janeDoe.setEmail("jane.doe@gmail.com");
            janeDoe.setPassword("password");
            janeDoe.setFirstName("Jane");
            janeDoe.setLastName("Doe");
            janeDoe.setRole(RoleFixture.getUser());
            janeDoe.setState(StateFixture.getPendingActivation());
            defaultPersistedUsers.add(janeDoe);
        }
        return defaultPersistedUsers;
    }

}

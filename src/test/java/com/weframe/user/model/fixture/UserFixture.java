package com.weframe.user.model.fixture;

import com.weframe.user.model.Role;
import com.weframe.user.model.User;

public class UserFixture {

    public static final Long JOHN_DOE_ID = 1L;
    public static final String JOHN_DOE_FIRST_NAME = "Jhon";
    public static final String JOHN_DOE_LAST_NAME = "Doe";
    public static final String JOHN_DOE_EMAIL = "john.doe@email.com";
    public static final String JOHN_DOE_PASSWORD = "123456";
    public static final Role JOHN_DOE_ROLE = RoleFixture.user();
    public static final boolean JOHN_DOE_ACTIVE = true;
    public static final Long JANE_DOE_ID = 2L;
    public static final String JANE_DOE_FIRST_NAME = "Jane";
    public static final String JANE_DOE_LAST_NAME = "Doe";
    public static final String JANE_DOE_EMAIL = "jane.doe@email.com";
    public static final String JANE_DOE_PASSWORD = "789012";
    public static final Role JANE_DOE_ROLE = RoleFixture.admin();
    public static final boolean JANE_DOE_ACTIVE = true;

    public static User johnDoe() {
        return new User(
                JOHN_DOE_ID,
                JOHN_DOE_FIRST_NAME,
                JOHN_DOE_LAST_NAME,
                JOHN_DOE_EMAIL,
                JOHN_DOE_PASSWORD,
                JOHN_DOE_ROLE,
                JOHN_DOE_ACTIVE);
    }

    public static User janeDoe() {
        return new User(
                JANE_DOE_ID,
                JANE_DOE_FIRST_NAME,
                JANE_DOE_LAST_NAME,
                JANE_DOE_EMAIL,
                JANE_DOE_PASSWORD,
                JANE_DOE_ROLE,
                JANE_DOE_ACTIVE);
    }

}

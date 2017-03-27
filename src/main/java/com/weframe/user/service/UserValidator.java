package com.weframe.user.service;

import com.weframe.user.model.User;
import org.apache.commons.lang3.StringUtils;

public class UserValidator {

    public boolean isValidInsert(User user) {
        return user != null &&
                !StringUtils.isBlank(user.getEmail()) &&
                !StringUtils.isBlank(user.getFirstName()) &&
                !StringUtils.isBlank(user.getLastName()) &&
                !StringUtils.isBlank(user.getPassword()) &&
                user.getRole() != null &&
                user.getState() != null;
    }

    public boolean isValidUpdate(User user) {
        return user != null &&
                !StringUtils.isBlank(user.getEmail()) &&
                !StringUtils.isBlank(user.getFirstName()) &&
                !StringUtils.isBlank(user.getLastName());
    }

}

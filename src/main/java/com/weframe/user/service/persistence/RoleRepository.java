package com.weframe.user.service.persistence;

import com.weframe.user.model.Role;

public interface RoleRepository {

    String DEFAULT_ROLE_NAME = "USER";

    Role getById(Long id);

    Role getByName(String name);

    default Role getDefaultRole() {
        return getByName(DEFAULT_ROLE_NAME);
    }

}

package com.weframe.user.service;

import com.weframe.user.model.Role;

public interface RoleService {

    Role getById(Long id);

    Role getByName(String name);

    default Role getDefaultRole() {
        return getByName("USER");
    }

}

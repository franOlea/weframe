package com.weframe.user.service.persistence;

import com.weframe.user.model.Role;

public interface RoleRepository {

    Role getById(Long id);

    Role getByName(String name);

    default Role getDefaultRole() {
        return getByName("USER");
    }

}

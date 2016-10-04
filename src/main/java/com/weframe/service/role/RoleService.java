package com.weframe.service.role;

import com.weframe.model.user.Role;

public interface RoleService {

    Role getById(Long id);

    Role getByName(String name);

    default Role getDefaultRole() {
        return getByName("USER");
    }

}

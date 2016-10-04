package com.weframe.service.role.impl;

import com.weframe.model.user.Role;
import com.weframe.service.role.RoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("orm")
public interface RoleRepository extends RoleService, JpaRepository<Role, Long> {

    default Role getByName(String name) {
        return findByName(name);
    }

    default Role getById(Long id) {
        return findOne(id);
    }

    Role findByName(String name);
}

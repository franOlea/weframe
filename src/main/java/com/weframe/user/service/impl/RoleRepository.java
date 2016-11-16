package com.weframe.user.service.impl;

import com.weframe.user.model.Role;
import com.weframe.user.service.RoleService;
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

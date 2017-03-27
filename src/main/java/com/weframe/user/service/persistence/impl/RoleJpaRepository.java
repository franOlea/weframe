package com.weframe.user.service.persistence.impl;

import com.weframe.user.model.Role;
import com.weframe.user.service.persistence.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("orm")
public interface RoleJpaRepository extends RoleRepository, JpaRepository<Role, Long> {

    default Role getByName(String name) {
        return findByName(name);
    }

    default Role getById(Long id) {
        return findOne(id);
    }

    Role findByName(String name);
}

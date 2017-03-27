package com.weframe.user.service.persistence.impl;

import com.weframe.user.model.State;
import com.weframe.user.service.persistence.StateRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("orm")
public interface StateJpaRepository extends StateRepository, JpaRepository<State, Long> {

    default State getByName(String name) {
        return findByName(name);
    }

    default State getById(Long id) {
        return findOne(id);
    }

    State findByName(String name);
    
}

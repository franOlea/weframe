package com.weframe.user.service.impl;

import com.weframe.user.model.State;
import com.weframe.user.service.StateService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("orm")
public interface StateRepository extends StateService, JpaRepository<State, Long> {

    default State getByName(String name) {
        return findByName(name);
    }

    default State getById(Long id) {
        return findOne(id);
    }

    State findByName(String name);
    
}

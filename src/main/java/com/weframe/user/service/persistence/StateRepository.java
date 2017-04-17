package com.weframe.user.service.persistence;

import com.weframe.user.model.State;

public interface StateRepository {

    State getById(Long id);

    State getByName(String name);

    default State getDefaultState() {
        return getByName("PENDING_ACTIVATION");
    }
    
}

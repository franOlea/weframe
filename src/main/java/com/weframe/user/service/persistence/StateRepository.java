package com.weframe.user.service.persistence;

import com.weframe.user.model.State;

public interface StateRepository {
    String ACTIVE_STATE = "ACTIVE";
    String INACTIVE_STATE = "INACTIVE";
    String PENDING_ACTIVATION_STATE = "PENDING_ACTIVATION";

    State getById(Long id);

    State getByName(String name);

    default State getDefaultState() {
        return getByName(PENDING_ACTIVATION_STATE);
    }
    
}

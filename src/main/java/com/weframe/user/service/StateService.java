package com.weframe.user.service;

import com.weframe.user.model.State;

public interface StateService {

    State getById(Long id);

    State getByName(String name);

    default State getDefaultState() {
        return getByName("PENDING_ACTIVATION");
    }
    
}

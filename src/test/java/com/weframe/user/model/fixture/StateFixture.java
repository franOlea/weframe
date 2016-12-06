package com.weframe.user.model.fixture;


import com.weframe.user.model.State;

public class StateFixture {

    public static final String PENDING_ACTIVATION = "PENDING_ACTIVATION";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final Long PENDING_ACTIVATION_ID = 1L;
    public static final Long ACTIVE_ID = 2L;
    public static final Long INACTIVE_ID = 3L;

    public static State pendingActivation() {
        return new State(PENDING_ACTIVATION_ID, PENDING_ACTIVATION);
    }

    public static State active() {
        return new State(ACTIVE_ID, ACTIVE);
    }

    public static State inactive() {
        return new State(INACTIVE_ID, INACTIVE);
    }

}

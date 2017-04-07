package com.weframe.user.fixture;

import com.weframe.user.model.State;

public class StateFixture {

    private static final State pendingActivation = new State(1, "PENDING_ACTIVATION");
    private static final State active = new State(2, "ACTIVE");
    private static final State inactive = new State(3, "INACTIVE");

    public static State getPendingActivation() {
        return pendingActivation;
    }

    public static State getActive() {
        return active;
    }

    public static State getInactive() {
        return inactive;
    }
}

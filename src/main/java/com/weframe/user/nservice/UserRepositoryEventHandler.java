package com.weframe.user.nservice;

import com.weframe.user.model.Role;
import com.weframe.user.model.State;
import com.weframe.user.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

@Component
@RepositoryEventHandler(User.class)
public class UserRepositoryEventHandler {

    private static final Logger logger = Logger.getLogger(UserRepositoryEventHandler.class);

    @Autowired
    private UserPasswordCryptographer userPasswordCryptographer;

    @Autowired
    private BeforeCreateUserValidator beforeCreateUserValidator;

    @Autowired
    private Role initialRole;

    @Autowired
    private State initialState;

    @HandleBeforeCreate
    public void handleUserCreation(User user) {
        try {
            user.setPassword(userPasswordCryptographer.generateStoringPasswordHash(user.getPassword()));
            user.setRole(initialRole);
            user.setState(initialState);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @HandleAfterCreate
    public void loggUserCreation(User user) {
        logger.info("Created and presisted user: " + user);
    }

}

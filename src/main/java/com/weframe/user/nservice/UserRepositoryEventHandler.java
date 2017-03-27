package com.weframe.user.nservice;

import com.weframe.user.model.Role;
import com.weframe.user.model.State;
import com.weframe.user.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

//@Component
//@RepositoryEventHandler(User.class)
public class UserRepositoryEventHandler {

    private static final Logger logger = Logger.getLogger(UserRepositoryEventHandler.class);

    @Autowired
    private UserRepository userRepository;

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
        user.setRole(initialRole);
        user.setState(initialState);
    }

    @HandleAfterCreate
    public void logUserCreation(User user) {
        logger.info("Created and presisted user: " + user.getEmail());
    }

    @HandleBeforeSave
    public void handleUserSave(User user) {
        User persisted = userRepository.findByEmail(user.getEmail());
        user.setPassword(persisted.getPassword());
        user.setState(persisted.getState());
        user.setRole(persisted.getRole());
        user.setId(persisted.getId());
    }

    @HandleAfterSave
    public void logUserSave(User user) {
        logger.info("Updated and saved user: " + user.getEmail());
    }

}

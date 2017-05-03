package com.weframe.user.service.persistence;

import com.weframe.controllers.EmptyResultException;
import com.weframe.user.model.User;
import com.weframe.user.service.UserPasswordCryptographer;
import com.weframe.user.service.UserValidator;
import com.weframe.user.service.persistence.exception.*;
import org.apache.commons.lang3.Validate;

import java.util.Collection;

@SuppressWarnings({"WeakerAccess"})
public abstract class UserService {

    protected final UserRepository userRepository;
    protected final RoleRepository roleRepository;
    protected final StateRepository stateRepository;
    protected final UserPasswordCryptographer passwordCryptographer;
    protected final UserValidator userValidator;

    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final StateRepository stateRepository,
                       final UserPasswordCryptographer passwordCryptographer,
                       final UserValidator userValidator) {
        Validate.notNull(userRepository, "The user repository cannot be null.");
        Validate.notNull(roleRepository, "The role repository cannot be null.");
        Validate.notNull(stateRepository, "The state repository cannot be null.");

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.stateRepository = stateRepository;
        this.passwordCryptographer = passwordCryptographer;
        this.userValidator = userValidator;
    }

    public abstract User getById(final Long id) throws EmptyResultException, InvalidUserPersistenceException;

    public abstract User getByEmail(final String email) throws EmptyResultException, InvalidUserPersistenceException;

    public abstract Collection<User> getAll(final int size, final int page) throws EmptyResultException, InvalidUserPersistenceException;

    public abstract void create(final User user) throws InvalidUserPersistenceException, ForbiddenOperationException, EmailAlreadyUsedException, InvalidFieldException;

    public abstract void update(final User user) throws InvalidUserPersistenceException, ForbiddenOperationException;

    public abstract void delete(final Long id) throws InvalidUserPersistenceException;

}

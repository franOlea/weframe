package com.weframe.user.service.persistence.impl;

import com.weframe.user.model.User;
import com.weframe.user.service.UserPasswordCryptographer;
import com.weframe.user.service.UserValidator;
import com.weframe.user.service.persistence.RoleRepository;
import com.weframe.user.service.persistence.StateRepository;
import com.weframe.user.service.persistence.UserRepository;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.EmptyResultException;
import com.weframe.user.service.persistence.exception.ForbiddenOperationException;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;

import java.security.GeneralSecurityException;
import java.util.Collection;

public class UserServiceImpl extends UserService {

    public UserServiceImpl(final UserRepository userRepository,
                           final RoleRepository roleRepository,
                           final StateRepository stateRepository,
                           final UserPasswordCryptographer passwordCryptographer,
                           final UserValidator userValidator) {
        super(userRepository, roleRepository, stateRepository, passwordCryptographer, userValidator);
    }

    @Override
    public User getById(final Long id) throws InvalidUserPersistenceException, EmptyResultException {
        final User user = userRepository.get(id);
        if(user == null) {
            throw new EmptyResultException();
        } else {
            return user;
        }
    }

    @Override
    public User getByEmail(final String email) throws EmptyResultException, InvalidUserPersistenceException {
        final User user = userRepository.get(email);
        if(user == null) {
            throw new EmptyResultException();
        } else {
            return user;
        }
    }

    @Override
    public Collection<User> getAll(final int size, final int page) throws EmptyResultException, InvalidUserPersistenceException {
        final Collection<User> users = userRepository.getAll(size, page);
        if(users == null || users.isEmpty()) {
            throw new EmptyResultException();
        } else {
            return users;
        }
    }

    @Override
    public void create(final User user) throws InvalidUserPersistenceException, ForbiddenOperationException {
        User persisted = userRepository.get(user.getEmail());
        if(persisted == null && userValidator.isValidInsert(user)) {
            user.setRole(roleRepository.getDefaultRole());
            user.setState(stateRepository.getDefaultState());
            try {
                user.setPassword(passwordCryptographer.generateStoringPasswordHash(user.getPassword()));
            } catch (GeneralSecurityException e) {
                throw new InvalidUserPersistenceException(e);
            }
            userRepository.persist(user);
        } else {
            throw new ForbiddenOperationException();
        }
    }

    @Override
    public void update(User user) throws InvalidUserPersistenceException, ForbiddenOperationException {
        User persisted = userRepository.get(user.getEmail());
        if(persisted != null && userValidator.isValidUpdate(user)) {
            persisted.setFirstName(user.getFirstName());
            persisted.setLastName(user.getLastName());
            userRepository.persist(persisted);
        } else {
            throw new ForbiddenOperationException();
        }
    }

    @Override
    public void delete(Long id) throws InvalidUserPersistenceException {
        userRepository.remove(id);
    }

}

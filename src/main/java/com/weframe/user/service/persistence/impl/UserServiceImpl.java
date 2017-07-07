package com.weframe.user.service.persistence.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.user.model.State;
import com.weframe.user.model.User;
import com.weframe.user.service.security.UserPasswordCryptographer;
import com.weframe.user.service.security.UserValidator;
import com.weframe.user.service.persistence.RoleRepository;
import com.weframe.user.service.persistence.StateRepository;
import com.weframe.user.service.persistence.UserRepository;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.security.GeneralSecurityException;
import java.util.Collection;

public class UserServiceImpl extends UserService {

    public UserServiceImpl(final UserRepository userRepository,
                           final RoleRepository roleRepository,
                           final StateRepository stateRepository,
                           final UserPasswordCryptographer passwordCryptographer,
                           final UserValidator userValidator) {
        super(
                userRepository,
                roleRepository,
                stateRepository,
                passwordCryptographer,
                userValidator
        );
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
    public User getByLogin(String email, String password) throws EmptyResultException, InvalidUserPersistenceException {
        final User user = getByEmail(email);
        if(user == null) {
            throw new EmptyResultException();
        } else try {
            if(passwordCryptographer.isValidPassword(password, user.getPassword())){
                return user;
            } else {
                return null;
            }
        } catch (GeneralSecurityException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    @Override
    public Collection<User> getAll(final int size, final int page)
            throws EmptyResultException, InvalidUserPersistenceException {
        final Collection<User> users = userRepository.getAll(size, page);
        if(users == null || users.isEmpty()) {
            throw new EmptyResultException();
        } else {
            return users;
        }
    }

    @Override
    public void create(final User user) throws InvalidUserPersistenceException,
                                               ForbiddenOperationException,
                                               EmailAlreadyUsedException,
                                               InvalidFieldException {
        userValidator.validateInsert(user);
        User persisted = userRepository.get(user.getEmail());
        if(persisted != null) {
            throw new EmailAlreadyUsedException();
        }
        User created = new User();
        created.setFirstName(user.getFirstName());
        created.setLastName(user.getLastName());
        created.setEmail(user.getEmail());
        created.setRole(roleRepository.getDefaultRole());
        created.setState(stateRepository.getDefaultState());
        try {
            created.setPassword(
                    passwordCryptographer.generateStoringPasswordHash(
                            user.getPassword()
                    )
            );
        } catch (GeneralSecurityException e) {
            throw new InvalidUserPersistenceException(e);
        }
        try {
            userRepository.persist(created);
        } catch(DataIntegrityViolationException e) {
            throw new ForbiddenOperationException();
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    @Override
    public void update(User user) throws InvalidUserPersistenceException,
                                         ForbiddenOperationException {
        User persisted = userRepository.get(user.getEmail());

        if(userValidator.isValidUpdate(user)) {
            if(persisted == null) {
                throw new InvalidUserPersistenceException();
            }
            persisted.setFirstName(user.getFirstName());
            persisted.setLastName(user.getLastName());
            userRepository.persist(persisted);
        } else {
            throw new ForbiddenOperationException();
        }
    }

    @Override
    public void changePassword(final String email, final String password) throws InvalidUserPersistenceException, EmptyResultException {
        try {
            User user = getByEmail(email);
            user.setPassword(passwordCryptographer.generateStoringPasswordHash(password));
            userRepository.persist(user);
        } catch (GeneralSecurityException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    @Override
    public void delete(Long id) throws InvalidUserPersistenceException {
        userRepository.remove(id);
    }

    @Override
    public void changeAccountState(final String email, final State state) throws EmptyResultException, InvalidUserPersistenceException {
        User user = getByEmail(email);
        user.setState(state);
        userRepository.persist(user);
    }

    @Override
    public void verifyAccount(String email) throws EmptyResultException, InvalidUserPersistenceException {
        changeAccountState(email, stateRepository.getByName(StateRepository.ACTIVE_STATE));
    }
}

package com.weframe.user.service.persistence;

import com.weframe.user.model.User;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;

import java.util.Collection;

public interface UserRepository {

    void persist(final User user) throws InvalidUserPersistenceException;

    void remove(final Long id) throws InvalidUserPersistenceException;

    User get(final Long id) throws InvalidUserPersistenceException;

    User get(final String email) throws InvalidUserPersistenceException;

    User get(final String email, final String password) throws InvalidUserPersistenceException;

    Collection<User> getAll(final int size, final int page) throws InvalidUserPersistenceException;

}

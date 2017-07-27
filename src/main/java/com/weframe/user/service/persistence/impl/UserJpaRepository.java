package com.weframe.user.service.persistence.impl;

import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserRepository;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@SuppressWarnings("unused")
@Profile("orm")
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    default void persist(final User user) throws InvalidUserPersistenceException {
        try {
            save(user);
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    default void remove(final Long id) throws InvalidUserPersistenceException {
        try {
            delete(id);
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    default User get(final Long id) throws InvalidUserPersistenceException {
        try {
            return findOne(id);
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    default User get(final String email) throws InvalidUserPersistenceException {
        try {
            return findByEmail(email);
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    default User get(final String email, final String password) throws InvalidUserPersistenceException {
        try {
            return findByEmailAndPassword(email, password);
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    default Collection<User> getAll(final int size, final int page) throws InvalidUserPersistenceException {
        try {
            return findAll(new PageRequest(page, size)).getContent();
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    @Override
    default Long getCount() throws InvalidUserPersistenceException {
        try {
            return count();
        } catch(DataAccessException e) {
            throw new InvalidUserPersistenceException(e);
        }
    }

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}

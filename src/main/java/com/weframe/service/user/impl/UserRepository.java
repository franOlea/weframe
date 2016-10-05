package com.weframe.service.user.impl;

import com.weframe.model.user.User;
import com.weframe.service.user.UserService;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

@Profile("orm")
public interface UserRepository extends JpaRepository<User, Long>, UserService {

    default void insert(final User user) throws InvalidUserPersistenceRequestException {
        if(!isValidInsert(user)) {
            throw new InvalidUserPersistenceRequestException();
        }

        try {
            user.setPassword(generateStoringPasswordHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidUserPersistenceRequestException();
        }

        save(user);
    }

    default void update(final User user) throws InvalidUserPersistenceRequestException {
        if(!isValidUpdate(user)) {
            throw new InvalidUserPersistenceRequestException();
        }

        User actual = getByEmail(user.getEmail());

        if(!actual.getEmail().equals(user.getEmail())) {
            throw new InvalidUserPersistenceRequestException();
        }

        user.setId(actual.getId());
        user.setPassword(actual.getPassword());
        user.setRole(actual.getRole());
        save(user);
    }

    default void changePassword(final String oldPassword,
                                final String newPassword,
                                final Long id) throws InvalidUserPersistenceRequestException {
        User user = getById(id);

        try {
            if(!isValidPassword(oldPassword, user.getPassword())) {
                throw new InvalidUserPersistenceRequestException();
            }

            user.setPassword(generateStoringPasswordHash(user.getPassword()));
            save(user);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidUserPersistenceRequestException();
        }
    }

    default void deleteById(final Long id) {
        if(id == null ||
                id < 1L) {
            throw new InvalidUserPersistenceRequestException();
        }

        delete(id);
    }

    default User getById(final Long id) {
        if(id == null ||
                id < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        return findOne(id);
    }

    default User getByEmail(final String email) {
        if(StringUtils.isBlank(email)) {
            throw new InvalidUserPersistenceRequestException();
        }

        return findByEmail(email);
    }

    default User getByLogin(final String email, final String password) {
        if(StringUtils.isBlank(email) ||
                StringUtils.isBlank(password)) {
            throw new InvalidUserPersistenceRequestException();
        }

        return findByEmailAndPassword(email, password);
    }

    default Collection<User> getAllWithPaging(final int offset, final int limit) {
        if(offset < 0 || limit < 1) {
            throw new InvalidUserPersistenceRequestException();
        }

        return findAll(new PageRequest(offset / limit, limit)).getContent();
    }

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}

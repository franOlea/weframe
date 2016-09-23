package com.weframe.service.user.impl;

import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@Profile("orm")
public interface UserRepository extends UserDao, JpaRepository<User, Long> {

    default void insert(final User user) {
        if(user == null ||
                !(user.getId() > 0 &&
                user.getEmail() != null &&
                user.getFirstName() != null &&
                user.getLastName() != null &&
                user.getPassword() != null &&
                user.getPasswordSalt() != null &&
                user.getRole() != null &&
                user.getRole().getId() > 0 &&
                user.getRole().getName() != null)) {
            throw new InvalidUserPersistenceRequestException();
        }

        save(user);
    }

    default void update(final User user) {
        if(user == null ||
                StringUtils.isBlank(user.getFirstName()) ||
                StringUtils.isBlank(user.getLastName())) {

            throw new InvalidUserPersistenceRequestException();
        }

        save(user);
    }

    default void delete(final Long id) {
        if(id == null ||
                id < 1) {
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

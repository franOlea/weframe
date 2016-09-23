package com.weframe.service.user.impl;

import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@Profile("orm")
public interface UserRepository extends UserDao, JpaRepository<User, Long> {

    default void insert(final User user) {
        save(user);
    }

    default void update(final User user) {
        save(user);
    }

    default void delete(final Long id) {
        delete(id);
    }

    default User getById(final Long id) {
        return findOne(id);
    }

    default User getByEmail(final String email) {
        return findByEmail(email);
    }

    default User getByLogin(final String email, final String password) {
        return findByEmailAndPassword(email, password);
    }

    default Collection<User> getAllWithPaging(final int offset, final int limit) {
        return findAll(new PageRequest(offset / limit, limit)).getContent();
    }

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}

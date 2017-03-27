package com.weframe.user.service.impl;

import com.weframe.user.model.User;
import com.weframe.user.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@SuppressWarnings("unused")
@Profile("orm")
public interface UserRepository extends JpaRepository<User, Long>, UserService {

    default void insert(final User user) {
        save(user);
    }

    default void update(final User user) {
        User persisted = getByEmail(user.getEmail());

        persisted.setFirstName(user.getFirstName());
        persisted.setLastName(user.getLastName());
        //actual.setEmail(user.getEmail());

        save(persisted);
    }

    default void changePassword(final String oldPassword,
                                final String newPassword,
                                final Long id) {
        User user = getById(id);

        save(user);
    }

    default void deleteById(final Long id) {
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

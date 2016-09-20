package com.weframe.service.user;

import com.weframe.model.user.User;

import java.util.Collection;

public interface UserDao {

    void insert(final User user);

    void update(final User user);

    void delete(final Long id);

    User getById(final Long id);

    User getByEmail(final String email);

    User getByLogin(final String email, final String password);

    Collection<User> getAllWithPaging(final int offset, final int limit);

}

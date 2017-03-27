package com.weframe.user.service;

import com.weframe.user.model.User;
import java.util.Collection;

public interface UserService {

    void insert(final User user);

    void update(final User user);

    void changePassword(final String oldPassword, final String newPassword, final Long id);

    void deleteById(final Long id);

    User getById(final Long id);

    User getByEmail(final String email);

    User getByLogin(final String email, final String password);

    Collection<User> getAllWithPaging(final int offset, final int limit);

}

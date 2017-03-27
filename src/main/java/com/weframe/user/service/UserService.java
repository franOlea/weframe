package com.weframe.user.service;

import com.weframe.user.model.User;
import com.weframe.user.service.exception.InvalidUserPersistenceRequestException;
import org.apache.commons.lang3.StringUtils;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

public interface UserService {

    void insert(final User user) throws InvalidUserPersistenceRequestException, GeneralSecurityException;

    void update(final User user) throws InvalidUserPersistenceRequestException;

    void changePassword(final String oldPassword, final String newPassword, final Long id)
            throws InvalidUserPersistenceRequestException, GeneralSecurityException;

    void deleteById(final Long id);

    User getById(final Long id);

    User getByEmail(final String email);

    User getByLogin(final String email, final String password);

    Collection<User> getAllWithPaging(final int offset, final int limit);

    default boolean isValidInsert(User user) {
        return user != null &&
                !StringUtils.isBlank(user.getEmail()) &&
                !StringUtils.isBlank(user.getFirstName()) &&
                !StringUtils.isBlank(user.getLastName()) &&
                !StringUtils.isBlank(user.getPassword()) &&
                user.getRole() != null &&
                user.getState() != null;
    }

    default boolean isValidUpdate(User user) {
        return user != null &&
                !StringUtils.isBlank(user.getEmail()) &&
                !StringUtils.isBlank(user.getFirstName()) &&
                !StringUtils.isBlank(user.getLastName());
    }
}

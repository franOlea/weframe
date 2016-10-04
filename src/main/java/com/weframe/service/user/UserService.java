package com.weframe.service.user;

import com.weframe.model.user.Role;
import com.weframe.model.user.User;
import com.weframe.service.role.RoleService;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

public interface UserService {

    int HASH_ITERATIONS = 1000;

    void insert(final User user);

    void update(final User user);

    void changePassword(final String oldPassword, final String newPassword, final Long id);

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
                user.getRole() != null;
    }

    default boolean isValidUpdate(User user) {
        return user != null &&
                !StringUtils.isBlank(user.getEmail()) &&
                !StringUtils.isBlank(user.getFirstName()) &&
                !StringUtils.isBlank(user.getLastName());
    }

    default String generateStoringPasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = HASH_ITERATIONS;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    default boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    default byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    default String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    default byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}

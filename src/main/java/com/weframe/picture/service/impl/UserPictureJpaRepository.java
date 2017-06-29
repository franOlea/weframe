package com.weframe.picture.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.UserPicture;
import com.weframe.picture.service.UserPictureRepository;
import com.weframe.picture.service.exception.InvalidUserPicturePersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserPictureJpaRepository extends UserPictureRepository, JpaRepository<UserPicture, Long> {

    @Override
    default UserPicture persist(final UserPicture userPicture) throws InvalidUserPicturePersistenceException {
        try {
            return save(userPicture);
        } catch(DataAccessException e) {
            throw new InvalidUserPicturePersistenceException(e);
        }
    }

    @Override
    default void remove(final Long id) throws InvalidUserPicturePersistenceException {
        try {
            delete(id);
        } catch(DataAccessException e) {
            throw new InvalidUserPicturePersistenceException(e);
        }
    }

    @Override
    default Set<UserPicture> getAllUserPictures(final String userEmail) throws InvalidUserPicturePersistenceException, EmptyResultException {
        try {
            Set<UserPicture> userPictures = findByUserEmail(userEmail);
            if(userPictures == null) {
                throw new EmptyResultException();
            } else {
                return userPictures;
            }
        } catch(DataAccessException e) {
            throw new InvalidUserPicturePersistenceException(e);
        }
    }

    @Override
    default UserPicture getUserPicture(final String userEmail, final String uniqueKey) throws EmptyResultException, InvalidUserPicturePersistenceException {
        try {
            UserPicture userPicture = findByUserEmailAndPictureImageKey(userEmail, uniqueKey);
            if(userPicture == null) {
                throw new EmptyResultException();
            } else {
                return userPicture;
            }
        } catch(DataAccessException e) {
            throw new InvalidUserPicturePersistenceException(e);
        }
    }

    Set<UserPicture> findByUserEmail(final String userEmail);
    UserPicture findByUserEmailAndPictureImageKey(final String userEmail, final String pictureImageKey);

}

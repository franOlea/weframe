package com.weframe.picture.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.UserPicture;
import com.weframe.picture.service.exception.InvalidUserPicturePersistenceException;

import java.util.Set;

public interface UserPictureRepository {

    UserPicture persist(final UserPicture userPicture) throws InvalidUserPicturePersistenceException;

    void remove(final Long id) throws InvalidUserPicturePersistenceException;

    Set<UserPicture> getAllUserPictures(final String userEmail) throws EmptyResultException, InvalidUserPicturePersistenceException;

    UserPicture getUserPicture(final String userEmail, final String uniqueKey) throws EmptyResultException, InvalidUserPicturePersistenceException;

}

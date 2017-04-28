package com.weframe.picture.service;

import com.weframe.picture.model.Picture;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.user.service.persistence.exception.EmptyResultException;

public interface PictureRepository {

    void persist(final Picture picture) throws InvalidPicturePersistenceException;

    void remove(final Long id) throws InvalidPicturePersistenceException;

    Picture get(final Long id) throws InvalidPicturePersistenceException;

    Picture get(final String uniqueName) throws InvalidPicturePersistenceException;

}

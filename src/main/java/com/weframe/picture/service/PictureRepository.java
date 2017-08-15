package com.weframe.picture.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;

public interface PictureRepository {

    Picture persist(final Picture picture) throws InvalidPicturePersistenceException;

    void remove(final Long id) throws InvalidPicturePersistenceException;

    Picture get(final Long id) throws InvalidPicturePersistenceException, EmptyResultException;

    Picture get(final String uniqueName) throws InvalidPicturePersistenceException, EmptyResultException;

    Long getCount() throws InvalidPicturePersistenceException;

}

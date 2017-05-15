package com.weframe.picture.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureJpaRepository extends PictureRepository, JpaRepository<Picture, Long> {

    @Override
    default Picture persist(final Picture picture) throws InvalidPicturePersistenceException {
        try {
            return save(picture);
        } catch(DataAccessException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    default void remove(final Long id) throws InvalidPicturePersistenceException {
        try {
            delete(id);
        } catch(DataAccessException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    default Picture get(final Long id) throws InvalidPicturePersistenceException, EmptyResultException {
        try {
            Picture picture = findOne(id);
            if(picture == null) {
                throw new EmptyResultException();
            } else {
                return picture;
            }
        } catch(DataAccessException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    default Picture get(final String uniqueName) throws InvalidPicturePersistenceException, EmptyResultException {
        try {
            Picture picture = findByImageKey(uniqueName);
            if(picture == null) {
                throw new EmptyResultException();
            } else {
                return picture;
            }
        } catch(DataAccessException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    Picture findByImageKey(final String uniqueName);
}

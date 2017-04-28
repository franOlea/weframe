package com.weframe.picture.service.impl;

import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureJpaRepository extends PictureRepository, JpaRepository<Picture, Long> {

    @Override
    default void persist(final Picture picture) throws InvalidPicturePersistenceException {
        try {
            save(picture);
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
    default Picture get(final Long id) throws InvalidPicturePersistenceException {
        try {
            return findOne(id);
        } catch(DataAccessException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    default Picture get(final String uniqueName) throws InvalidPicturePersistenceException {
        try {
            return findByUniqueName(uniqueName);
        } catch(DataAccessException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    Picture findByUniqueName(final String uniqueName);
}

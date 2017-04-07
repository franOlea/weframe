package com.weframe.product.service.impl;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.BackBoardRepository;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackBoardJpaRepository extends JpaRepository<BackBoard, Long>, BackBoardRepository {

    @Override
    default void persist(final BackBoard backBoard) throws InvalidGenericProductPersistenceException {
        try {
            save(backBoard);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    default void remove(final BackBoard backBoard) throws InvalidGenericProductPersistenceException {
        try {
            delete(backBoard);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    default BackBoard get(final Long id) throws InvalidGenericProductPersistenceException {
        try {
            return findOne(id);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    default BackBoard get(final String uniqueName)throws InvalidGenericProductPersistenceException {
        try {
            return findByUniqueName(uniqueName);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    BackBoard findByUniqueName(final String uniqueName);
}

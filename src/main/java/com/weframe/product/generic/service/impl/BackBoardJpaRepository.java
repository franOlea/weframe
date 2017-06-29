package com.weframe.product.generic.service.impl;

import com.weframe.product.generic.model.BackBoard;
import com.weframe.product.generic.service.GenericProductRepository;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BackBoardJpaRepository extends GenericProductRepository<BackBoard>, JpaRepository<BackBoard, Long> {

    @Override
    default void persist(final BackBoard backBoard) throws InvalidGenericProductPersistenceException {
        try {
            save(backBoard);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    default void remove(final Long id) throws InvalidGenericProductPersistenceException {
        try {
            delete(id);
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
    default BackBoard get(final String uniqueName) throws InvalidGenericProductPersistenceException {
        try {
            return findByUniqueName(uniqueName);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    default Collection<BackBoard> getAll(final int size,
                                         final int page) throws InvalidGenericProductPersistenceException {
        try {
            return findAll(new PageRequest(page, size)).getContent();
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    default Collection<BackBoard> getAllWtihNameLike(final String name, final int size, final int page) throws InvalidGenericProductPersistenceException {
        try {
            return findByName(name, new PageRequest(page, size)).getContent();
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    BackBoard findByUniqueName(final String uniqueName);
    Page<BackBoard> findByName(final String name, final Pageable pageable);

}

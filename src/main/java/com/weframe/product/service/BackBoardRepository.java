package com.weframe.product.service;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

public interface BackBoardRepository {

    void persist(final BackBoard backBoard) throws InvalidGenericProductPersistenceException;

    void remove(final BackBoard backBoard) throws InvalidGenericProductPersistenceException;

    BackBoard get(final Long id) throws InvalidGenericProductPersistenceException;

    BackBoard get(final String uniqueName) throws InvalidGenericProductPersistenceException;

}

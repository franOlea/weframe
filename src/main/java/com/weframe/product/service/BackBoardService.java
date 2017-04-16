package com.weframe.product.service;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public abstract class BackBoardService {

    protected final BackBoardRepository backBoardRepository;

    public BackBoardService(final BackBoardRepository backBoardRepository) {
        this.backBoardRepository = backBoardRepository;
    }

    public abstract BackBoard getById(final Long id) throws InvalidGenericProductPersistenceException;

    public abstract BackBoard getByUniqueName(final String uniqueName) throws InvalidGenericProductPersistenceException;

    public abstract Collection<BackBoard> getAll(final int size, final int page);

    public abstract void create(final BackBoard backBoard);

    public abstract void update(final BackBoard backBoard);

    public abstract void delete(final Long id);

}

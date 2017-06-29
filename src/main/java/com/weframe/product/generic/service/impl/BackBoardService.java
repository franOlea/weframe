package com.weframe.product.generic.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.generic.model.BackBoard;
import com.weframe.product.generic.service.GenericProductRepository;
import com.weframe.product.generic.service.GenericProductService;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class BackBoardService extends GenericProductService<BackBoard> {

    public BackBoardService(final GenericProductRepository<BackBoard> repository) {
        super(repository);
    }

    @Override
    public BackBoard getById(final Long id)
            throws InvalidGenericProductPersistenceException, EmptyResultException {
        BackBoard backBoard = repository.get(id);
        if(backBoard == null) {
            throw new EmptyResultException();
        }

        return backBoard;
    }

    @Override
    public BackBoard getByUniqueName(final String uniqueName)
            throws InvalidGenericProductPersistenceException, EmptyResultException {
        BackBoard backBoard = repository.get(uniqueName);
        if(backBoard == null) {
            throw new EmptyResultException();
        }

        return backBoard;
    }

    @Override
    public Collection<BackBoard> getAll(final int page,
                                             final int size) throws InvalidGenericProductPersistenceException, EmptyResultException {
        Collection<BackBoard> backBoards = repository.getAll(size, page);

        if(backBoards == null || backBoards.isEmpty()) {
            throw new EmptyResultException();
        }

        return backBoards;
    }

    @Override
    public Collection<BackBoard> getAllWithNameLike(final String name,
                                                         final int page,
                                                         final int size) throws InvalidGenericProductPersistenceException, EmptyResultException {
        Collection<BackBoard> backBoards = repository.getAllWtihNameLike(name, size, page);

        if(backBoards == null || backBoards.isEmpty()) {
            throw new EmptyResultException();
        }

        return backBoards;
    }

    @Override
    public void persist(final BackBoard backBoard) throws InvalidGenericProductPersistenceException {
        BackBoard persisted = null;
        if(backBoard.getId() != null) {
            persisted = repository.get(backBoard.getId());
        } else if(backBoard.getUniqueName() != null) {
            persisted = repository.get(backBoard.getUniqueName());
        }

        if(persisted == null) {
            repository.persist(backBoard);
        } else {
            persisted.setM2Price(backBoard.getM2Price());
            persisted.setName(backBoard.getName());
            persisted.setDescription(backBoard.getDescription());
            persisted.setPicture(backBoard.getPicture());
            persisted.setUniqueName(backBoard.getUniqueName());
            repository.persist(persisted);
        }
    }

    @Override
    public void delete(final Long id) throws InvalidGenericProductPersistenceException {
        repository.remove(id);
    }

}

package com.weframe.product.service.impl;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.GenericProductService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class BackBoardService extends GenericProductService<BackBoard> {

    public BackBoardService(final GenericProductRepository<BackBoard> repository) {
        super(repository);
    }

    @Override
    public BackBoard getById(final Long id) throws InvalidGenericProductPersistenceException {
        return repository.get(id);
    }

    @Override
    public BackBoard getByUniqueName(final String uniqueName) throws InvalidGenericProductPersistenceException {
        return repository.get(uniqueName);
    }

    @Override
    public Collection<BackBoard> getAll(final int page,
                                             final int size) throws InvalidGenericProductPersistenceException {
        return repository.getAll(size, page);
    }

    @Override
    public Collection<BackBoard> getAllWithNameLike(final String name,
                                                         final int page,
                                                         final int size) throws InvalidGenericProductPersistenceException {
        return repository.getAllWtihNameLike(name, size, page);
    }

    @Override
    public void persist(final BackBoard genericProduct) throws InvalidGenericProductPersistenceException {
        repository.persist(genericProduct);
    }

    @Override
    public void delete(final Long id) throws InvalidGenericProductPersistenceException {
        repository.remove(id);
    }

}

package com.weframe.product.service.impl;

import com.weframe.product.model.GenericProduct;
import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.GenericProductService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class DefaultGenericProductService extends GenericProductService<GenericProduct> {

    public DefaultGenericProductService(final GenericProductRepository<GenericProduct> repository) {
        super(repository);
    }

    @Override
    public GenericProduct getById(final Long id) throws InvalidGenericProductPersistenceException {
        return repository.get(id);
    }

    @Override
    public GenericProduct getByUniqueName(final String uniqueName) throws InvalidGenericProductPersistenceException {
        return repository.get(uniqueName);
    }

    @Override
    public Collection<GenericProduct> getAll(final int page,
                                             final int size) throws InvalidGenericProductPersistenceException {
        return repository.getAll(size, page);
    }

    @Override
    public Collection<GenericProduct> getAllWithNameLike(final String name,
                                                         final int page,
                                                         final int size) throws InvalidGenericProductPersistenceException {
        return repository.getAllWtihNameLike(name, size, page);
    }

    @Override
    public void persist(final GenericProduct genericProduct) throws InvalidGenericProductPersistenceException {
        repository.persist(genericProduct);
    }

    @Override
    public void delete(final Long id) throws InvalidGenericProductPersistenceException {
        repository.remove(id);
    }

}

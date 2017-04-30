package com.weframe.product.service.impl;

import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.GenericProductService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class DefaultGenericProductService<T> extends GenericProductService<T> {

    public DefaultGenericProductService(final GenericProductRepository<T> repository) {
        super(repository);
    }

    @Override
    public T getById(final Long id) throws InvalidGenericProductPersistenceException {
        return repository.get(id);
    }

    @Override
    public T getByUniqueName(final String uniqueName) throws InvalidGenericProductPersistenceException {
        return repository.get(uniqueName);
    }

    @Override
    public Collection<T> getAll(final int page,
                                final int size) throws InvalidGenericProductPersistenceException {
        return repository.getAll(size, page);
    }

    @Override
    public Collection<T> getAllWithNameLike(final String name,
                                            final int page,
                                            final int size) throws InvalidGenericProductPersistenceException {
        return repository.getAllWtihNameLike(name, size, page);
    }

    @Override
    public void persist(final T t) throws InvalidGenericProductPersistenceException {
        repository.persist(t);
    }

    @Override
    public void delete(final Long id) throws InvalidGenericProductPersistenceException {
        repository.remove(id);
    }
}

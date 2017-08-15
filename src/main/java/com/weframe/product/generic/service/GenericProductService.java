package com.weframe.product.generic.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.generic.model.GenericProduct;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public abstract class GenericProductService<T extends GenericProduct> {

    protected final GenericProductRepository<T> repository;

    public GenericProductService(final GenericProductRepository<T> repository) {
        this.repository = repository;
    }

    public abstract Long getCount() throws InvalidGenericProductPersistenceException;

    public abstract T getById(final Long id)
            throws InvalidGenericProductPersistenceException, EmptyResultException;

    public abstract T getByUniqueName(final String uniqueName)
            throws InvalidGenericProductPersistenceException, EmptyResultException;

    public abstract Collection<T> getAll(final int page,
                                         final int size)
            throws InvalidGenericProductPersistenceException, EmptyResultException;

    public abstract Collection<T> getAllWithNameLike(final String name,
                                         final int page,
                                         final int size)
            throws InvalidGenericProductPersistenceException, EmptyResultException;

    public abstract void persist(T t)
            throws InvalidGenericProductPersistenceException;

    public abstract void delete(Long id)
            throws InvalidGenericProductPersistenceException;
}

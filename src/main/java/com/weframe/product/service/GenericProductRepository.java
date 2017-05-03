package com.weframe.product.service;

import com.weframe.product.model.GenericProduct;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public interface GenericProductRepository<T extends GenericProduct> {

    void persist(final T t)
            throws InvalidGenericProductPersistenceException;

    void remove(final Long id)
            throws InvalidGenericProductPersistenceException;

    T get(final Long id)
            throws InvalidGenericProductPersistenceException;

    T get(final String uniqueName)
            throws InvalidGenericProductPersistenceException;

    Collection<T> getAll(int size, int page)
            throws InvalidGenericProductPersistenceException;

    Collection<T> getAllWtihNameLike(final String name, final int size, final int page)
            throws InvalidGenericProductPersistenceException;
}

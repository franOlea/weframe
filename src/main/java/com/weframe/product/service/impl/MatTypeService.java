package com.weframe.product.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.model.generic.MatType;
import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.GenericProductService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class MatTypeService extends GenericProductService<MatType> {

    public MatTypeService(GenericProductRepository<MatType> repository) {
        super(repository);
    }

    @Override
    public MatType getById(Long id) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.get(id);
    }

    @Override
    public MatType getByUniqueName(String uniqueName) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.get(uniqueName);
    }

    @Override
    public Collection<MatType> getAll(int page, int size) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.getAll(size, page);
    }

    @Override
    public Collection<MatType> getAllWithNameLike(String name, int page, int size) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.getAllWtihNameLike(name, size, page);
    }

    @Override
    public void persist(MatType matType) throws InvalidGenericProductPersistenceException {
        repository.persist(matType);
    }

    @Override
    public void delete(Long id) throws InvalidGenericProductPersistenceException {
        repository.remove(id);
    }
}

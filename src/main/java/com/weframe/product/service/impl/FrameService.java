package com.weframe.product.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.model.generic.Frame;
import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.GenericProductService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class FrameService extends GenericProductService<Frame> {

    public FrameService(final GenericProductRepository<Frame> repository) {
        super(repository);
    }

    @Override
    public Frame getById(Long id) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.get(id);
    }

    @Override
    public Frame getByUniqueName(String uniqueName) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.get(uniqueName);
    }

    @Override
    public Collection<Frame> getAll(int page, int size) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.getAll(size, page);
    }

    @Override
    public Collection<Frame> getAllWithNameLike(String name, int page, int size) throws InvalidGenericProductPersistenceException, EmptyResultException {
        return repository.getAllWtihNameLike(name, size, page);
    }

    @Override
    public void persist(Frame frame) throws InvalidGenericProductPersistenceException {
        repository.persist(frame);
    }

    @Override
    public void delete(Long id) throws InvalidGenericProductPersistenceException {
        repository.remove(id);
    }
}

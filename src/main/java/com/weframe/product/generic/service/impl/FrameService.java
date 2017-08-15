package com.weframe.product.generic.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.product.generic.model.Frame;
import com.weframe.product.generic.service.GenericProductRepository;
import com.weframe.product.generic.service.GenericProductService;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;

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
    public Long getCount() throws InvalidGenericProductPersistenceException {
        return repository.getCount();
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

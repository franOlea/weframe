package com.weframe.product.service.impl;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.BackBoardRepository;
import com.weframe.product.service.BackBoardService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;

import java.util.Collection;

public class BackBoardServiceImpl extends BackBoardService {

    public BackBoardServiceImpl(BackBoardRepository backBoardRepository) {
        super(backBoardRepository);
    }

    @Override
    public BackBoard getById(Long id) throws InvalidGenericProductPersistenceException {
        return backBoardRepository.get(id);
    }

    @Override
    public BackBoard getByUniqueName(String uniqueName) throws InvalidGenericProductPersistenceException {
        return backBoardRepository.get(uniqueName);
    }

    @Override
    public Collection<BackBoard> getAll(int size, int page) {
        return null;
    }

    @Override
    public void create(BackBoard backBoard) {

    }

    @Override
    public void update(BackBoard backBoard) {

    }

    @Override
    public void delete(Long id) {

    }
}

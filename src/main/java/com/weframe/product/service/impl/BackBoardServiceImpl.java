package com.weframe.product.service.impl;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.BackBoardRepository;
import com.weframe.product.service.BackBoardService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public class BackBoardServiceImpl extends BackBoardService {

    public BackBoardServiceImpl(final BackBoardRepository backBoardRepository) {
        super(backBoardRepository);
    }

    @Override
    public BackBoard getById(Long id) throws InvalidGenericProductPersistenceException {
        try {
            return backBoardRepository.get(id);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    public BackBoard getByUniqueName(String uniqueName) throws InvalidGenericProductPersistenceException {
        try {
            return backBoardRepository.get(uniqueName);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    public Collection<BackBoard> getAll(int size, int page) throws InvalidGenericProductPersistenceException {
        try {
            return backBoardRepository.getAll(page, size);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    public void create(BackBoard backBoard) throws InvalidGenericProductPersistenceException {
        try {
            backBoardRepository.persist(backBoard);
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }

    @Override
    public void update(BackBoard backBoard) throws InvalidGenericProductPersistenceException {
        BackBoard persisted = backBoardRepository.get(backBoard.getUniqueName());
        if(persisted != null) {
            persisted.setName(backBoard.getName());
            persisted.setUniqueName(backBoard.getUniqueName());
            persisted.setDescription(backBoard.getDescription());
            persisted.setM2Price(backBoard.getM2Price());
            persisted.setPicture(backBoard.getPicture());
            try {
                backBoardRepository.persist(persisted);
            } catch(DataAccessException e) {
                throw new InvalidGenericProductPersistenceException(e);
            }

        }
    }

    @Override
    public void delete(Long id) throws InvalidGenericProductPersistenceException {
        try {
            backBoardRepository.remove(
                    backBoardRepository.get(id)
            );
        } catch(DataAccessException e) {
            throw new InvalidGenericProductPersistenceException(e);
        }
    }
}

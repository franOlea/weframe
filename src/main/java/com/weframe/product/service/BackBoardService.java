package com.weframe.product.service;

import com.weframe.product.model.BackBoard;

import java.util.Set;

public interface BackBoardService {

    BackBoard getById(Long id);
    void insert(BackBoard backBoard);
    void remove(Long id);
    Set<BackBoard> getByName(String name);

}

package com.weframe.picture.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;

import java.io.File;

public abstract class PictureService {

    protected final PictureRepository repository;
    protected final PictureFileRepository fileRepository;

    public PictureService(final PictureRepository repository,
                          final PictureFileRepository fileRepository) {
        this.repository = repository;
        this.fileRepository = fileRepository;
    }

    public abstract Picture getById(final Long id) throws EmptyResultException, InvalidPicturePersistenceException;

    public abstract Picture getByUniqueName(final String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException;

    public abstract Picture create(final File pictureFile, String uniqueName) throws InvalidPicturePersistenceException;

    public abstract void delete(final Long id) throws InvalidPicturePersistenceException;

}

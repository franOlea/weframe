package com.weframe.picture.service;

import com.weframe.picture.model.Picture;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.user.service.persistence.exception.EmptyResultException;

import java.io.File;

public abstract class PictureService {

    protected final PictureRepository pictureRepository;
    protected final PictureFileRepository pictureFileRepository;

    public PictureService(final PictureRepository pictureRepository,
                          final PictureFileRepository pictureFileRepository) {
        this.pictureRepository = pictureRepository;
        this.pictureFileRepository = pictureFileRepository;
    }

    public abstract Picture getById(final Long id) throws EmptyResultException, InvalidPicturePersistenceException;

    public abstract Picture getByUniqueName(final String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException;

    public abstract void create(final File pictureFile, String uniqueName) throws InvalidPicturePersistenceException;

    public abstract void delete(final Long id) throws InvalidPicturePersistenceException;

}

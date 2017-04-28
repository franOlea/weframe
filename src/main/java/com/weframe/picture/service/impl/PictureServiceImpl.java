package com.weframe.picture.service.impl;

import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.user.service.persistence.exception.EmptyResultException;

import java.io.File;

public class PictureServiceImpl extends PictureService {

    public PictureServiceImpl(final PictureRepository pictureRepository,
                              final PictureFileRepository pictureFileRepository) {
        super(pictureRepository, pictureFileRepository);
    }

    @Override
    public Picture getById(Long id) throws EmptyResultException, InvalidPicturePersistenceException {
        return pictureRepository.get(id);
    }

    @Override
    public Picture getByUniqueName(String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException {
        return pictureRepository.get(uniqueName);
    }

    @Override
    public void create(File pictureFile, String uniqueName) throws InvalidPicturePersistenceException {
        String url = pictureFileRepository.putFile(pictureFile, uniqueName);
        pictureRepository.persist(new Picture(uniqueName, url));
    }

    @Override
    public void delete(Long id) throws InvalidPicturePersistenceException {
        pictureRepository.remove(id);
    }

}

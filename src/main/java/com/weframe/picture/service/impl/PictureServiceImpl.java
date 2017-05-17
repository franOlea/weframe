package com.weframe.picture.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.picture.service.exception.PictureFileIOException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class PictureServiceImpl extends PictureService {

    public PictureServiceImpl(final PictureRepository repository,
                              final PictureFileRepository fileRepository,
                              final String thumbnailSuffix,
                              final int thumbnailWidth,
                              final int thumbnailHeight) {
        super(repository, fileRepository, thumbnailSuffix, thumbnailWidth, thumbnailHeight);
    }

    @Override
    public Picture getById(Long id) throws EmptyResultException, InvalidPicturePersistenceException {
        return setPictureUrl(repository.get(id));
    }

    @Override
    public Picture getByUniqueName(String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException {
        return setPictureUrl(repository.get(uniqueName));
    }

    @Override
    public Picture setPictureUrl(final Picture picture) throws InvalidPicturePersistenceException {
        try {
            picture.setImageUrl(
                    fileRepository.getPictureUrl(
                            picture.getImageKey()
                    )
            );

            return picture;
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public Picture setPictureThumbnailUrl(Picture picture) throws InvalidPicturePersistenceException {
        try {
            picture.setImageUrl(
                    fileRepository.getPictureUrl(
                            picture.getImageKey() + thumbnailSuffix
                    )
            );

            return picture;
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public Picture create(BufferedImage bufferedImage, String uniqueName) throws InvalidPicturePersistenceException {
        try {
            fileRepository.putPicture(bufferedImage, uniqueName);
            fileRepository.putPicture(createThumbnail(bufferedImage), uniqueName + thumbnailSuffix);
            return repository.persist(new Picture(uniqueName));
        } catch (PictureFileIOException | IOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public void delete(Long id) throws InvalidPicturePersistenceException {
        repository.remove(id);
    }

}

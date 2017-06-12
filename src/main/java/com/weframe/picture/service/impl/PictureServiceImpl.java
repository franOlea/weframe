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
    public Picture getById(final Long id) throws EmptyResultException, InvalidPicturePersistenceException {
        return repository.get(id);
    }

    @Override
    public Picture getByUniqueName(final String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException {
        return repository.get(uniqueName);
    }

    @Override
    public String getPictureUrl(final String pictureUniqueKey) throws InvalidPicturePersistenceException {
        try {
            return fileRepository.getPictureUrl(pictureUniqueKey);
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public String getPictureThumbnailUrl(final String pictureUniqueKey) throws InvalidPicturePersistenceException {
        try {
            return fileRepository.getPictureUrl(pictureUniqueKey + thumbnailSuffix);
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public Picture create(final BufferedImage bufferedImage,
                          final String uniqueName,
                          final String imageFormatName) throws InvalidPicturePersistenceException {
        try {
            fileRepository.putPicture(bufferedImage, uniqueName, imageFormatName);
            fileRepository.putPicture(createThumbnail(bufferedImage), uniqueName + thumbnailSuffix, imageFormatName);
            return repository.persist(new Picture(uniqueName));
        } catch (PictureFileIOException | IOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public void delete(final Long id) throws InvalidPicturePersistenceException {
        try {
            Picture picture = repository.get(id);
            fileRepository.deletePicture(picture.getImageKey());
            fileRepository.deletePicture(picture.getImageKey() + thumbnailSuffix);
            repository.remove(id);
        } catch (PictureFileIOException | EmptyResultException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

}

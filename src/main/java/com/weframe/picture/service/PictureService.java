package com.weframe.picture.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class PictureService {

    protected final String thumbnailSuffix;
    private final int thumbnailWidth;
    private final int thumbnailHeight;

    protected final PictureRepository repository;
    protected final PictureFileRepository fileRepository;

    public PictureService(final PictureRepository repository,
                          final PictureFileRepository fileRepository,
                          final String thumbnailSuffix,
                          final int thumbnailWidth,
                          final int thumbnailHeight) {
        this.repository = repository;
        this.fileRepository = fileRepository;
        this.thumbnailSuffix = thumbnailSuffix;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    public abstract Picture getById(final Long id) throws EmptyResultException, InvalidPicturePersistenceException;

    public abstract Picture getByUniqueName(final String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException;

    public abstract String getPictureUrl(final String pictureUniqueKey, final boolean thumbnail) throws InvalidPicturePersistenceException;

    public abstract Picture create(final BufferedImage bufferedImage,
                                   final String uniqueName,
                                   final String imageFormatName) throws InvalidPicturePersistenceException;

    public abstract void delete(final Long id) throws InvalidPicturePersistenceException;

    public abstract Long getCount() throws InvalidPicturePersistenceException;

    protected BufferedImage createThumbnail(final BufferedImage original) throws IOException {
        return Thumbnails.of(original)
                .size(thumbnailWidth, thumbnailHeight)
                .asBufferedImage();
    }

}

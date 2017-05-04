package com.weframe.picture.service.impl;

import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.picture.service.exception.PictureFileIOException;
import com.weframe.user.service.persistence.exception.EmptyResultException;

import java.io.File;

public class PictureServiceImpl extends PictureService {

    public PictureServiceImpl(final PictureRepository repository,
                              final PictureFileRepository fileRepository) {
        super(repository, fileRepository);
    }

    @Override
    public Picture getById(Long id) throws EmptyResultException, InvalidPicturePersistenceException {
        try {
            return setPictureUrl(repository.get(id));
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public Picture getByUniqueName(String uniqueName) throws EmptyResultException, InvalidPicturePersistenceException {
        try {
            return setPictureUrl(repository.get(uniqueName));
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    private Picture setPictureUrl(final Picture picture) throws PictureFileIOException {
        picture.setImageUrl(
                fileRepository.getFileUrl(
                        picture.getImageKey()
                )
        );

        return picture;
    }

    @Override
    public void create(File pictureFile, String uniqueName) throws InvalidPicturePersistenceException {
        try {
            fileRepository.putFile(pictureFile, uniqueName);
            repository.persist(new Picture(uniqueName));
        } catch (PictureFileIOException e) {
            throw new InvalidPicturePersistenceException(e);
        }
    }

    @Override
    public void delete(Long id) throws InvalidPicturePersistenceException {
        repository.remove(id);
    }

}

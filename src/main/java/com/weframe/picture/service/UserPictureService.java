package com.weframe.picture.service;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.UserPicture;
import com.weframe.picture.service.exception.InvalidUserPicturePersistenceException;
import com.weframe.user.service.persistence.UserService;

import java.util.Set;

public abstract class UserPictureService {

    protected final PictureService pictureService;
    protected final UserService userService;
    protected final UserPictureRepository userPictureRepository;


    public UserPictureService(final PictureService pictureService,
                              final UserService userService,
                              final UserPictureRepository userPictureRepository) {
        this.pictureService = pictureService;
        this.userService = userService;
        this.userPictureRepository = userPictureRepository;
    }

    public abstract UserPicture create(final String email, final String imageKey) throws InvalidUserPicturePersistenceException;

    public abstract void delete(final Long id) throws InvalidUserPicturePersistenceException;

    public abstract Set<UserPicture> getAllUserPicturesByUserEmail(final String userEmail) throws InvalidUserPicturePersistenceException, EmptyResultException;

    public abstract UserPicture getUserPicture(final String userEmail, final String uniqueKey) throws InvalidUserPicturePersistenceException, EmptyResultException;

}

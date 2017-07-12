package com.weframe.picture.service.impl;

import com.weframe.controllers.EmptyResultException;
import com.weframe.picture.model.Picture;
import com.weframe.picture.model.UserPicture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.UserPictureRepository;
import com.weframe.picture.service.UserPictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.picture.service.exception.InvalidUserPicturePersistenceException;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;

import java.util.Set;

public class UserPictureServiceImpl extends UserPictureService {

    public UserPictureServiceImpl(final PictureService pictureService,
                                  final UserService userService,
                                  final UserPictureRepository userPictureRepository) {
        super(pictureService, userService, userPictureRepository);
    }

    @Override
    public UserPicture create(final String email, final String imageKey)
            throws InvalidUserPicturePersistenceException {
        try {
            User user = userService.getByEmail(email);
            Picture picture = pictureService.getByUniqueName(imageKey);
            UserPicture userPicture = new UserPicture(picture, user);

            return userPictureRepository.persist(userPicture);
        } catch (EmptyResultException
                | InvalidPicturePersistenceException
                | InvalidUserPersistenceException e) {
            throw new InvalidUserPicturePersistenceException(e);
        }
    }

    @Override
    public void delete(final String userEmail, final String uniqueKey) throws InvalidUserPicturePersistenceException, EmptyResultException {
        userPictureRepository.remove(
                userPictureRepository.getUserPicture(userEmail, uniqueKey).getId()
        );
    }

    @Override
    public Set<UserPicture> getAllUserPicturesByUserEmail(String userEmail) throws InvalidUserPicturePersistenceException, EmptyResultException {
        return userPictureRepository.getAllUserPictures(userEmail);
    }

    @Override
    public UserPicture getUserPicture(String userEmail, String uniqueKey) throws InvalidUserPicturePersistenceException, EmptyResultException {
        return userPictureRepository.getUserPicture(userEmail, uniqueKey);
    }
}

package com.weframe.picture.controller;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.controllers.errors.Error;
import com.weframe.picture.model.Picture;
import com.weframe.picture.model.UserPicture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.UserPictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.picture.service.exception.InvalidUserPicturePersistenceException;
import com.weframe.user.service.security.UserIdentityResolver;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/user-pictures")
@CrossOrigin
public class UserPictureController {

    private static final Logger logger = Logger.getLogger(PictureController.class);

    private final UserPictureService userPictureService;
    private final PictureService pictureService;
    private final ResponseGenerator<Picture> responseGenerator;
    private final UserIdentityResolver userIdentityResolver;

    public UserPictureController(final UserPictureService userPictureService,
                                 final PictureService pictureService,
                                 final ResponseGenerator<Picture> responseGenerator,
                                 final UserIdentityResolver userIdentityResolver) {
        this.userPictureService = userPictureService;
        this.pictureService = pictureService;
        this.responseGenerator = responseGenerator;
        this.userIdentityResolver = userIdentityResolver;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "file") final MultipartFile multipartFile,
                                 @RequestParam(value = "uniqueName") final String uniqueName,
                                 @RequestParam(value = "formatName") final String imageFormatName,
                                 final Authentication authentication) {
        try {
            String identityEmail = userIdentityResolver.resolve(authentication);
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            pictureService.create(image, uniqueName, imageFormatName);
            userPictureService.create(identityEmail, uniqueName);
        } catch (IOException
                | InvalidPicturePersistenceException
                | InvalidUserPicturePersistenceException e) {
            logger.error("There was an unexpected error while trying to transfer the multipart file to buffered image.", e);

            Error error = new Error(
                    "internal-server-error",
                    "There was an internal server error, please try again later."
            );
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return responseGenerator.generateOkResponse();
    }

    @RequestMapping(value = "/{uniqueName}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("uniqueName") final String uniqueName,
                                 final Authentication authentication) {
        try {
            String identityEmail = userIdentityResolver.resolve(authentication);
            userPictureService.delete(identityEmail, uniqueName);
        } catch (InvalidUserPicturePersistenceException | EmptyResultException e) {
            logger.error("There was an unexpected error while trying to delete the picture file.", e);

            Error error = new Error(
                    "internal-server-error",
                    "There was an internal server error, please try again later."
            );
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return responseGenerator.generateOkResponse();
    }

    @RequestMapping(value = "/{uniqueName}", method = RequestMethod.GET)
    public ResponseEntity getPicture(@PathVariable("uniqueName") final String uniqueName,
                                     @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize,
                                     final Authentication authentication) {
        try {
            UserPicture userPicture = userPictureService.getUserPicture(authentication.getName(), uniqueName);
            if(originalSize) {
                userPicture.getPicture().setImageUrl(
                        pictureService.getPictureUrl(userPicture.getPicture().getImageKey())
                );
            } else {
                userPicture.getPicture().setImageUrl(
                        pictureService.getPictureThumbnailUrl(userPicture.getPicture().getImageKey())
                );
            }
            return responseGenerator.generateResponse(userPicture.getPicture());
        } catch (InvalidUserPicturePersistenceException | InvalidPicturePersistenceException e) {
            logger.error("There was an unexpected error while trying to delete the picture file.", e);

            Error error = new Error(
                    "internal-server-error",
                    "There was an internal server error, please try again later."
            );
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getAllPictures(
            @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize,
            final Authentication authentication) {
        try {
            Set<Picture> pictures = userPictureService.getAllUserPicturesByUserEmail(authentication.getName())
                    .stream()
                    .map(UserPicture::getPicture)
                    .collect(Collectors.toSet());

            for(Picture picture : pictures) {
                if(originalSize) {
                    picture.setImageUrl(
                            pictureService.getPictureUrl(picture.getImageKey())
                    );
                } else {
                    picture.setImageUrl(
                            pictureService.getPictureThumbnailUrl(picture.getImageKey())
                    );
                }
            }

            return responseGenerator.generateResponse(pictures);
        } catch (InvalidUserPicturePersistenceException | InvalidPicturePersistenceException e) {
            logger.error("There was an unexpected error while trying to delete the picture file.", e);

            Error error = new Error(
                    "internal-server-error",
                    "There was an internal server error, please try again later."
            );
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }
}

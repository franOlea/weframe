package com.weframe.picture.controller;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.model.Picture;
import com.weframe.picture.model.UserPicture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.UserPictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.picture.service.exception.InvalidUserPicturePersistenceException;
import com.weframe.user.service.security.UserIdentityResolver;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    @RequestMapping(value = "/count/{userEmail}", method = RequestMethod.GET)
    private ResponseEntity getCount(@PathVariable("userEmail") final String userEmail) {
        try {
            return responseGenerator.generateCountResponse(userPictureService.getCount(userEmail));
        } catch (InvalidUserPicturePersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "file") final MultipartFile multipartFile,
                                 @RequestParam(value = "uniqueName") final String uniqueName,
                                 @RequestParam(value = "formatName") final String imageFormatName,
                                 final Authentication authentication) {
        try {
            String userEmailIdentity = resolveUserIdentity(authentication);
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            Picture created = pictureService.create(image, uniqueName, imageFormatName);
            userPictureService.create(userEmailIdentity, uniqueName);
            logger.debug(String.format(
                    "User [%s] Image [%s] created with ID [%s].", 
                    userEmailIdentity, created.getImageKey(), created.getId()
            ));
            return responseGenerator.generateOkResponse();
        } catch (IOException
                | InvalidPicturePersistenceException
                | InvalidUserPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{uniqueName}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("uniqueName") final String uniqueName,
                                 final Authentication authentication) {
        try {
            String userEmailIdentity = resolveUserIdentity(authentication);
            userPictureService.delete(userEmailIdentity, uniqueName);
            logger.debug(String.format(
                    "User [%s] Image [%s] deleted.", userEmailIdentity, uniqueName
            ));
            return responseGenerator.generateOkResponse();
        } catch (InvalidUserPicturePersistenceException | EmptyResultException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{uniqueName}", method = RequestMethod.GET)
    public ResponseEntity getPicture(@PathVariable("uniqueName") final String uniqueName,
                                     @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize,
                                     final Authentication authentication) {
        try {
            String userEmailIdentity = resolveUserIdentity(authentication);
            Picture picture = userPictureService.getUserPicture(userEmailIdentity, uniqueName).getPicture();
            picture.setImageUrl(
                    pictureService.getPictureUrl(picture.getImageKey(), !originalSize)
            );
            logger.debug(String.format(
                    "User [%s] %s image with unique name [%s] requested.", 
                    userEmailIdentity, originalSize ? "Original" : "Thumbnail", uniqueName
            ));
            return responseGenerator.generateResponse(picture);
        } catch (InvalidUserPicturePersistenceException | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    private String resolveUserIdentity(final Authentication authentication) {
        return userIdentityResolver.resolve(authentication);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getAllPictures(
            @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") final int size,
            final Authentication authentication) {
        try {
            String userEmailIdentity = resolveUserIdentity(authentication);
            Set<Picture> pictures = userPictureService.getAllUserPicturesByUserEmail(userEmailIdentity)
                    .stream()
                    .map(UserPicture::getPicture)
                    .collect(Collectors.toSet());
            assignUrlsToPictures(originalSize, pictures);
            logger.debug(String.format(
                    "User [%s] %s page %s size %s images requested.",
                    userEmailIdentity, page, size, originalSize ? "original" : "thumbnail"
            ));
            return responseGenerator.generateResponse(pictures);
        } catch (InvalidUserPicturePersistenceException | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on user pictures.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }

    private void assignUrlsToPictures(final boolean originalSize,
                                      final Set<Picture> pictures) throws InvalidPicturePersistenceException {
        for(Picture picture : pictures) {
            picture.setImageUrl(
                    pictureService.getPictureUrl(picture.getImageKey(), !originalSize)
            );
        }
    }
}

package com.weframe.picture.controller;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/pictures")
@CrossOrigin
public class PictureController {

    private static final Logger logger = Logger.getLogger(PictureController.class);

    private final PictureService service;
    private final ResponseGenerator<Picture> responseGenerator;

    public PictureController(final PictureService service,
                             final ResponseGenerator<Picture> responseGenerator) {
        this.service = service;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "file")final MultipartFile multipartFile,
                                 @RequestParam(value = "uniqueName") final String uniqueName,
                                 @RequestParam(value = "formatName") final String imageFormatName) {
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            Picture created = service.create(image, uniqueName, imageFormatName);
            logger.debug(String.format(
                    "Image [%s] created with ID [%s].", created.getImageKey(), created.getId())
            );
            return responseGenerator.generateOkResponse();
        } catch (IOException | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{pictureId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("pictureId") Long id) {
        try {
            service.delete(id);
            logger.debug(String.format("Image [%s] deleted.", id));
            return responseGenerator.generateOkResponse();
        } catch (InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{pictureId}", method = RequestMethod.GET)
    public ResponseEntity getPicture(@PathVariable("pictureId") final Long pictureId,
                                     @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            logger.debug(String.format(
                    "%s image with ID [%s] requested.", originalSize ? "Original" : "Thumbnail", pictureId)
            );
            Picture picture = service.getById(pictureId);
            picture.setImageUrl(service.getPictureUrl(picture.getImageKey(), !originalSize));
            return responseGenerator.generateResponse(picture);
        } catch (InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getPicture(@RequestParam(name = "uniqueName") final String uniqueName,
                                     @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            logger.debug(String.format(
                    "%s image with unique name [%s] requested.", originalSize ? "Original" : "Thumbnail", uniqueName)
            );
            Picture picture = service.getByUniqueName(uniqueName);
            picture.setImageUrl(service.getPictureUrl(picture.getImageKey(), !originalSize));
            return responseGenerator.generateResponse(picture);
        } catch (InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on pictures.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }

}

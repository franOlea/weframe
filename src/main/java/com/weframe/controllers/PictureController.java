package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/pictures")
@CrossOrigin
public class PictureController {

    private static final Logger logger = Logger.getLogger(PictureController.class);

    private final String tempDirectory;
    private final PictureService service;
    private final ResponseGenerator<Picture> responseGenerator;

    public PictureController(@Qualifier("tempDirectory") final String tempDirectory,
                             final PictureService service,
                             final ResponseGenerator<Picture> responseGenerator) {
        this.tempDirectory = tempDirectory;
        this.service = service;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "file")final MultipartFile multipartFile,
                                 @RequestParam(value = "uniqueName") final String uniqueName) {
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            //Todo if image format is not jpeg return error.
            service.create(image, uniqueName);
        } catch (IOException | InvalidPicturePersistenceException e) {
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

    @RequestMapping(value = "/{pictureId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("pictureId") Long id) {
        try {
            service.delete(id);
        } catch (InvalidPicturePersistenceException e) {
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

    @RequestMapping(value = "/{pictureId}", method = RequestMethod.GET)
    public ResponseEntity getPicture(@PathVariable("pictureId") final Long pictureId,
                                     @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            Picture picture = service.getById(pictureId);
            if(originalSize) {
                picture.setImageUrl(service.getPictureUrl(picture.getImageKey()));
            } else {
                picture.setImageUrl(service.getPictureThumbnailUrl(picture.getImageKey()));
            }
            return responseGenerator.generateResponse(service.getById(pictureId));
        } catch (InvalidPicturePersistenceException e) {
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

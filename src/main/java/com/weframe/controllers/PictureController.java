package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/pictures")
@CrossOrigin
public class PictureController {

    private static final Logger logger = Logger.getLogger(PictureController.class);

    private final String tempDirectory;
    private final PictureService service;
    private final ResponseGenerator<Picture> responseGenerator;

    public PictureController(final String tempDirectory,
                             final PictureService service,
                             final ResponseGenerator<Picture> responseGenerator) {
        this.tempDirectory = tempDirectory;
        this.service = service;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "file")final MultipartFile multipartFile,
                                 @RequestParam(value = "uniqueName") final String uniqueName) {
        File file = new File(tempDirectory + File.pathSeparatorChar + UUID.randomUUID());
        try {
            multipartFile.transferTo(file);
            service.create(file, uniqueName);
        } catch (IOException | InvalidPicturePersistenceException e) {
            logger.error("There was an unexpected error while trying to transfer the multipart file to the temp file.", e);

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

}

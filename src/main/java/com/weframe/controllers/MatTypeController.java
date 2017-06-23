package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.product.model.generic.MatType;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.service.impl.MatTypeService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/generic-product/mattypes")
@CrossOrigin
public class MatTypeController {

    private static final Logger logger = Logger.getLogger(MatTypeController.class);

    private final MatTypeService matTypeService;
    private final PictureService pictureService;
    private final ResponseGenerator<MatType> responseGenerator;

    public MatTypeController(final MatTypeService matTypeService,
                             final PictureService pictureService,
                             final ResponseGenerator<MatType> responseGenerator) {
        this.matTypeService = matTypeService;
        this.pictureService = pictureService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getMatTypees(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="unique-name", required = false) final String uniqueName) {
        try {
            if(uniqueName != null) {
                return getMatTypeByUniqueName(uniqueName);
            }

            if(page < 0 || size < 0) {
                Error error = new Error(
                        "invalid-request",
                        "The page and size parameters must be above zero."
                );
                return responseGenerator.generateErrorResponse(
                        Collections.singleton(error),
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }

            return responseGenerator.generateResponse(
                    matTypeService.getAll(page, size)
            );
        } catch (InvalidGenericProductPersistenceException e) {
            logger.error(
                    String.format(
                            "There has been an error requesting backboards by page [%d] " +
                                    "with size [%d]",
                            page,
                            size),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. " +
                            "Please try again later.");
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "/{matTypeId}", method = RequestMethod.GET)
    private ResponseEntity getMatType(@PathVariable Long matTypeId) {
        try {
            MatType matType = matTypeService.getById(matTypeId);
            return responseGenerator.generateResponse(
                    matTypeService.getById(matTypeId)
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to fetch a " +
                                    "mattype by id [%d].",
                            matTypeId
                    ),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    private ResponseEntity getMatTypeByUniqueName(String matTypeUniqueName) {
        try {
            return responseGenerator.generateResponse(
                    matTypeService.getByUniqueName(matTypeUniqueName)
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to fetch a " +
                                    "mattype by uniqueName [%s].",
                            matTypeUniqueName
                    ),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody MatType matType) {
        try {
            if(matType.getId() != null) {
                throw new InvalidGenericProductPersistenceException(
                        "A mattype to be created should not have an id."
                );
            }
            matType.setPicture(pictureService.getByUniqueName(matType.getPicture().getImageKey()));
            matTypeService.persist(matType);
            return responseGenerator.generateOkResponse();
        } catch(InvalidGenericProductPersistenceException |
                InvalidPicturePersistenceException |
                EmptyResultException e) {
            logger.error(
                    String.format(
                            "There has been an error creating the mattype [%s]",
                            matType.getUniqueName()),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. " +
                            "Please try again later.");
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    private ResponseEntity update(@RequestBody MatType matType) {
        try {
            if(matType.getId() == null
                    && matType.getUniqueName() == null) {
                throw new InvalidGenericProductPersistenceException(
                        "A mattype to be updated should have an id or the unique name set."
                );
            }
            matType.setPicture(pictureService.getByUniqueName(matType.getPicture().getImageKey()));
            matTypeService.persist(matType);
            return responseGenerator.generateOkResponse();
        } catch(InvalidGenericProductPersistenceException |
                InvalidPicturePersistenceException |
                EmptyResultException e) {
            logger.error(
                    String.format(
                            "There has been an error creating the mattype [%s]",
                            matType.getUniqueName()),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. " +
                            "Please try again later.");
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @RequestMapping(value = "/{matTypeId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long matTypeId) {
        try {
            matTypeService.delete(matTypeId);
            logger.info("Deleted mattype [" + matTypeId + "].");
            return responseGenerator.generateOkResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to delete a mattype" +
                                    " by id [%d].",
                            matTypeId),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return responseGenerator.generateErrorResponse(
                    Collections.singleton(error),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

}

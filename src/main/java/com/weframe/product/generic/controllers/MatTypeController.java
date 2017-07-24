package com.weframe.product.generic.controllers;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.product.generic.model.MatType;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.generic.service.impl.MatTypeService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/generic-product/mat-types")
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
    private ResponseEntity getMatTypes(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="unique-name", required = false) final String uniqueName,
            @RequestParam(value="original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            if(uniqueName != null) {
                return getMatTypeByUniqueName(uniqueName, originalSize);
            }

            if(page < 0 || size < 0) {
                return responseGenerator.generatePageRequestErrorResponse();
            }

            Collection<MatType> matTypes = matTypeService.getAll(page, size);
            assignPictureUrl(matTypes, !originalSize);
            logger.debug(String.format(
                    "MatTypes page %s size %s with %s size requested.",
                    page, size, originalSize ? "original" : "thumbnail"
            ));
            return responseGenerator.generateResponse(matTypes);
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "/{matTypeId}", method = RequestMethod.GET)
    private ResponseEntity getMatType(@PathVariable final Long matTypeId,
                                      @RequestParam(value="original", required = false, defaultValue = "false")
                                      final boolean originalSize) {
        try {
            MatType matType = matTypeService.getById(matTypeId);
            assignPictureUrl(matType, originalSize);
            logger.debug(String.format(
                    "MatType [%s] with %s size requested.",
                    matTypeId, originalSize ? "original" : "thumbnail"
            ));
            return responseGenerator.generateResponse(matType);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException  e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity getMatTypeByUniqueName(final String matTypeUniqueName,
                                                  final boolean originalSize) {
        try {
            MatType matType = matTypeService.getByUniqueName(matTypeUniqueName);
            assignPictureUrl(matType, originalSize);
            logger.debug(String.format(
                    "MatType [%s] with %s size requested.",
                    matTypeUniqueName, originalSize ? "original" : "thumbnail"
            ));
            return responseGenerator.generateResponse(matType);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody final MatType matType) {
        try {
            if(matType.getId() != null) {
                throw new InvalidGenericProductPersistenceException(
                        "A mattype to be created should not have an id."
                );
            }
            matType.setPicture(pictureService.getByUniqueName(matType.getPicture().getImageKey()));
            matTypeService.persist(matType);
            logger.debug(String.format(
                    "MatType [%s] created.",
                    matType.getUniqueName()
            ));
            return responseGenerator.generateOkResponse();
        } catch (EmptyResultException
                | InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
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
            logger.debug(String.format(
                    "MAtType [%s] updated.",
                    matType.getUniqueName()
            ));
            return responseGenerator.generateOkResponse();
        } catch (EmptyResultException
                | InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{matTypeId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long matTypeId) {
        try {
            matTypeService.delete(matTypeId);
            logger.debug(String.format(
                    "MAtType [%s] deleted.",
                    matTypeId
            ));
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private void assignPictureUrl(final MatType matType, final boolean thumbnail) throws InvalidPicturePersistenceException {
        matType.getPicture().setImageUrl(
                pictureService.getPictureUrl(matType.getPicture().getImageKey(), thumbnail)
        );
    }

    private void assignPictureUrl(final Collection<MatType> matTypes, final boolean thumbnail) throws InvalidPicturePersistenceException {
        for(MatType matType : matTypes) {
            assignPictureUrl(matType, thumbnail);
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on matTypes.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }

}

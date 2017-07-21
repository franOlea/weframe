package com.weframe.product.generic.controllers;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.product.generic.model.BackBoard;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.generic.service.impl.BackBoardService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/generic-product/backboards")
@CrossOrigin
public class BackBoardController {

    private static final Logger logger = Logger.getLogger(BackBoardController.class);
    
    private final BackBoardService backBoardService;
    private final PictureService pictureService;
    private final ResponseGenerator<BackBoard> responseGenerator;

    public BackBoardController(final BackBoardService backBoardService,
                               final PictureService pictureService,
                               final ResponseGenerator<BackBoard> responseGenerator) {
        this.backBoardService = backBoardService;
        this.pictureService = pictureService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getBackBoards(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="unique-name", required = false) final String uniqueName,
            @RequestParam(value="original", required = false, defaultValue = "false") final boolean isOriginalSize) {
        try {
            if(uniqueName != null) {
                return getBackBoardByUniqueName(uniqueName, isOriginalSize);
            }
            if(page < 0 || size < 0) {
                return responseGenerator.generatePageRequestErrorResponse();
            }
            Collection<BackBoard> backBoards = backBoardService.getAll(page, size);
            assignPictureUrl(backBoards, !isOriginalSize);
            return responseGenerator.generateResponse(backBoards);
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "/{backBoardId}", method = RequestMethod.GET)
    private ResponseEntity getBackBoard(
            @PathVariable Long backBoardId,
            @RequestParam(name = "original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            BackBoard backBoard = backBoardService.getById(backBoardId);
            assignPictureUrl(backBoard, !originalSize);
            return responseGenerator.generateResponse(
                    backBoardService.getById(backBoardId)
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity getBackBoardByUniqueName(String backBoardUniqueName, boolean isOriginalSize) {
        try {
            BackBoard backBoard = backBoardService.getByUniqueName(backBoardUniqueName);
            assignPictureUrl(backBoard, !isOriginalSize);
            return responseGenerator.generateResponse(backBoard);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody BackBoard backBoard) {
        try {
            if(backBoard.getId() != null) {
                throw new InvalidGenericProductPersistenceException(
                        "A backboard to be created should not have an id."
                );
            }
            Picture picture = pictureService.getByUniqueName(
                    backBoard.getPicture().getImageKey()
            );
            backBoard.setPicture(picture);
            backBoardService.persist(backBoard);
            return responseGenerator.generateOkResponse();
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    private ResponseEntity update(@RequestBody BackBoard backBoard) {
        try {
            if(backBoard.getId() == null
                    && backBoard.getUniqueName() == null) {
                throw new InvalidGenericProductPersistenceException(
                        "A backboard to be updated should have an id or the unique name set."
                );
            }
            Picture picture = pictureService.getByUniqueName(
                    backBoard.getPicture().getImageKey()
            );
            backBoard.setPicture(picture);
            backBoardService.persist(backBoard);
            return responseGenerator.generateOkResponse();
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{backBoardId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long backBoardId) {
        try {
            backBoardService.delete(backBoardId);
            logger.info("Deleted backboard [" + backBoardId + "].");
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private void assignPictureUrl(final BackBoard backBoard, final boolean thumbnail) throws InvalidPicturePersistenceException {
        backBoard.getPicture().setImageUrl(
                pictureService.getPictureUrl(backBoard.getPicture().getImageKey(), thumbnail)
        );
    }

    private void assignPictureUrl(final Collection<BackBoard> backBoards, final boolean thumbnail) throws InvalidPicturePersistenceException {
        for(BackBoard backBoard : backBoards) {
            assignPictureUrl(backBoard, thumbnail);
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on user pictures.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }
    
}

package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.controllers.errors.ErrorResponse;
import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.BackBoardService;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.user.service.persistence.exception.EmptyResultException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;


@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/backboards")
@CrossOrigin
public class BackBoardController {

    private static final Logger logger = Logger.getLogger(BackBoardController.class);
    
    private final BackBoardService backBoardService;
    private final ResponseGenerator<BackBoard> responseGenerator;

    public BackBoardController(final BackBoardService backBoardService,
                               final ResponseGenerator<BackBoard> responseGenerator) {
        this.backBoardService = backBoardService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getUsers(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size) {
        if(page < 0 || size < 0) {
            Error error = new Error(
                    "invalid-request",
                    "The page and size parameters must be above zero."
            );
            return responseGenerator.createErrorResponse(
                    Collections.singleton(error), 
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
        return getUsersWithPaging(page, size);
    }

    private ResponseEntity getUsersWithPaging(final int page, final int size) {
        try {
            Collection<BackBoard> backBoards = backBoardService.getAll(size, page);
            if(backBoards != null || !backBoards.isEmpty()) {
                return generateResponse(backBoards);
            } else {
                return responseGenerator.generateEmptyResponse();
            }
        } catch (InvalidGenericProductPersistenceException e) {
            logger.error(
                    String.format(
                        "There has been an error requesting back boards by page [%d] with size [%d]",
                            page,
                            size),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return responseGenerator.createErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity<Collection<BackBoard>> generateResponse(
            final Collection<BackBoard> backBoards) {
        return new ResponseEntity<>(backBoards, new HttpHeaders(), HttpStatus.OK);
    }
    
}

package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.product.model.generic.Frame;
import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.service.impl.FrameService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/frames")
@CrossOrigin
public class FrameController {

    private static final Logger logger = Logger.getLogger(FrameController.class);

    private final FrameService frameService;
    private final ResponseGenerator<Frame> responseGenerator;

    public FrameController(final FrameService frameService,
                           final ResponseGenerator<Frame> responseGenerator) {
        this.frameService = frameService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getFrame(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="unique-name", required = false) final String uniqueName) {
        try {
            if(uniqueName != null) {
                return getFrameByUniqueName(uniqueName);
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
                    frameService.getAll(page, size)
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

    @RequestMapping(value = "/{frameId}", method = RequestMethod.GET)
    private ResponseEntity getFrame(@PathVariable Long frameId) {
        try {
            return responseGenerator.generateResponse(
                    frameService.getById(frameId)
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to fetch a " +
                                    "frame by id [%d].",
                            frameId
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

    private ResponseEntity getFrameByUniqueName(String frameUniqueName) {
        try {
            return responseGenerator.generateResponse(
                    frameService.getByUniqueName(frameUniqueName)
            );
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to fetch a " +
                                    "frame by uniqueName [%s].",
                            frameUniqueName
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
    private ResponseEntity create(@RequestBody Frame frame) {
        try {
            if(frame.getId() != null) {
                throw new InvalidGenericProductPersistenceException(
                        "A frame to be created should not have an id."
                );
            }
            frameService.persist(frame);
            return responseGenerator.generateOkResponse();
        } catch(InvalidGenericProductPersistenceException e) {
            logger.error(
                    String.format(
                            "There has been an error creating the frame [%s]",
                            frame.getUniqueName()),
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
    private ResponseEntity update(@RequestBody Frame frame) {
        try {
            if(frame.getId() == null
                    && frame.getUniqueName() == null) {
                throw new InvalidGenericProductPersistenceException(
                        "A frame to be updated should have an id or the unique name set."
                );
            }
            frameService.persist(frame);
            return responseGenerator.generateOkResponse();
        } catch(InvalidGenericProductPersistenceException e) {
            logger.error(
                    String.format(
                            "There has been an error creating the frame [%s]",
                            frame.getUniqueName()),
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

    @RequestMapping(value = "/{frameId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long frameId) {
        try {
            frameService.delete(frameId);
            logger.info("Deleted frame [" + frameId + "].");
            return responseGenerator.generateOkResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to delete a frame" +
                                    " by id [%d].",
                            frameId),
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

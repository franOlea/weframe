package com.weframe.product.generic.controllers;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exception.InvalidPicturePersistenceException;
import com.weframe.product.generic.model.Frame;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.generic.service.impl.FrameService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/generic-product/frames")
@CrossOrigin
public class FrameController {

    private static final Logger logger = Logger.getLogger(FrameController.class);

    private final FrameService frameService;
    private final PictureService pictureService;
    private final ResponseGenerator<Frame> responseGenerator;

    public FrameController(final FrameService frameService,
                           final PictureService pictureService,
                           final ResponseGenerator<Frame> responseGenerator) {
        this.frameService = frameService;
        this.pictureService = pictureService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getFrame(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="unique-name", required = false) final String uniqueName,
            @RequestParam(value="original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            if(uniqueName != null) {
                return getFrameByUniqueName(uniqueName, originalSize);
            }

            if(page < 0 || size < 0) {
                return responseGenerator.generatePageRequestErrorResponse();
            }

            Collection<Frame> frames = frameService.getAll(page, size);
            assignPictureUrl(frames, !originalSize);
            return responseGenerator.generateResponse(frames);
        }  catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{frameId}", method = RequestMethod.GET)
    private ResponseEntity getFrame(@PathVariable final Long frameId,
                                    @RequestParam(value="original", required = false, defaultValue = "false") final boolean originalSize) {
        try {
            Frame frame = frameService.getById(frameId);
            assignPictureUrl(frame, originalSize);
            return responseGenerator.generateResponse(frame);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity getFrameByUniqueName(final String frameUniqueName, final boolean originalSize) {
        try {
            Frame frame = frameService.getByUniqueName(frameUniqueName);
            assignPictureUrl(frame, originalSize);
            return responseGenerator.generateResponse(frame);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
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
            frame.setPicture(pictureService.getByUniqueName(frame.getPicture().getImageKey()));
            frameService.persist(frame);
            return responseGenerator.generateOkResponse();
        } catch (EmptyResultException
                | InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
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
            frame.setPicture(pictureService.getByUniqueName(frame.getPicture().getImageKey()));
            frameService.persist(frame);
            return responseGenerator.generateOkResponse();
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException
                | InvalidPicturePersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{frameId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long frameId) {
        try {
            frameService.delete(frameId);
            logger.debug("Deleted frame [" + frameId + "].");
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private void assignPictureUrl(final Frame frame, final boolean thumbnail) throws InvalidPicturePersistenceException {
        frame.getPicture().setImageUrl(
                pictureService.getPictureUrl(frame.getPicture().getImageKey(), thumbnail)
        );
    }

    private void assignPictureUrl(final Collection<Frame> frames, final boolean thumbnail) throws InvalidPicturePersistenceException {
        for(Frame frame : frames) {
            assignPictureUrl(frame, thumbnail);
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on frames.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }

}

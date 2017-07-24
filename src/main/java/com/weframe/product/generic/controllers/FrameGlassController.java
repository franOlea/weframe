package com.weframe.product.generic.controllers;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.product.generic.model.FrameGlass;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.generic.service.impl.FrameGlassService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/generic-product/frameglasses")
@CrossOrigin
public class FrameGlassController {

    private static final Logger logger = Logger.getLogger(FrameGlassController.class);

    private final FrameGlassService frameGlassService;
    private final ResponseGenerator<FrameGlass> responseGenerator;

    public FrameGlassController(final FrameGlassService frameGlassService,
                               final ResponseGenerator<FrameGlass> responseGenerator) {
        this.frameGlassService = frameGlassService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getFrameGlasses(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="unique-name", required = false) final String uniqueName) {
        try {
            if(uniqueName != null) {
                return getFrameGlassByUniqueName(uniqueName);
            }
            if(page < 0 || size < 0) {
                responseGenerator.generatePageRequestErrorResponse();
            }
            Collection<FrameGlass> frameGlasses = frameGlassService.getAll(page, size);
            logger.debug(String.format(
                    "Frames page %s size %s requested.", page, size
            ));
            return responseGenerator.generateResponse(frameGlasses);
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "/{frameGlassId}", method = RequestMethod.GET)
    private ResponseEntity getFrameGlass(@PathVariable final Long frameGlassId) {
        try {
            FrameGlass frameGlass = frameGlassService.getById(frameGlassId);
            logger.debug(String.format(
                    "FrameGlass [%s] requested.",
                    frameGlassId
            ));
            return responseGenerator.generateResponse(frameGlass);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity getFrameGlassByUniqueName(final String frameGlassUniqueName) {
        try {
            FrameGlass frameGlass = frameGlassService.getByUniqueName(frameGlassUniqueName);
            logger.debug(String.format(
                    "FrameGlass [%s] requested.",
                    frameGlassUniqueName
            ));
            return responseGenerator.generateResponse(frameGlass);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (Exception e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody FrameGlass frameGlass) {
        try {
            if(frameGlass.getId() != null) {
                throw new InvalidGenericProductPersistenceException(
                        "A frameglass to be created should not have an id."
                );
            }
            frameGlassService.persist(frameGlass);
            logger.debug(String.format(
                    "FrameGlass [%s] created.",
                    frameGlass.getUniqueName()
            ));
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    private ResponseEntity update(@RequestBody FrameGlass frameGlass) {
        try {
            if(frameGlass.getId() == null
                    && frameGlass.getUniqueName() == null) {
                throw new InvalidGenericProductPersistenceException(
                        "A frameglass to be updated should have an id or the unique name set."
                );
            }
            frameGlassService.persist(frameGlass);
            logger.debug(String.format(
                    "FrameGlass [%s] updated.",
                    frameGlass.getUniqueName()
            ));
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{frameGlassId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long frameGlassId) {
        try {
            frameGlassService.delete(frameGlassId);
            logger.debug(String.format(
                    "FrameGlass [%s] deleted.",
                    frameGlassId
            ));
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on frameGlasses.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }

}

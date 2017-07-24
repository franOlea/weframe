package com.weframe.product.generic.controllers;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.product.generic.model.FrameGlass;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.generic.service.impl.FrameGlassService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return responseGenerator.generateResponse(frameGlassService.getAll(page, size));
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        }
    }

    @RequestMapping(value = "/{frameGlassId}", method = RequestMethod.GET)
    private ResponseEntity getFrameGlass(@PathVariable Long frameGlassId) {
        try {
            return responseGenerator.generateResponse(frameGlassService.getById(frameGlassId));
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity getFrameGlassByUniqueName(String frameGlassUniqueName) {
        try {
            return responseGenerator.generateResponse(frameGlassService.getByUniqueName(frameGlassUniqueName));
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
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    @RequestMapping(value = "/{frameGlassId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long frameGlassId) {
        try {
            frameGlassService.delete(frameGlassId);
            logger.info("Deleted frameglass [" + frameGlassId + "].");
            return responseGenerator.generateOkResponse();
        } catch (InvalidGenericProductPersistenceException e) {
            return handleUnexpectedError(e);
        }
    }

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while doing an operation on frames.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }

}

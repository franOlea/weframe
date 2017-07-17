package com.weframe.product.pictureframe.controller;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.PictureFrameService;
import com.weframe.product.pictureframe.service.exception.InvalidPictureFramePersistenceException;
import com.weframe.user.service.security.UserIdentityResolver;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/picture-frames")
@CrossOrigin
public class PictureFrameController {
	private static final Logger logger = Logger.getLogger(PictureFrameController.class);

	private final PictureFrameService pictureFrameService;
	private final UserIdentityResolver userIdentityResolver;

	private final ResponseGenerator<PictureFrame> responseGenerator;

	public PictureFrameController(final PictureFrameService pictureFrameService,
								  final UserIdentityResolver userIdentityResolver,
								  final ResponseGenerator<PictureFrame> responseGenerator) {
		this.pictureFrameService = pictureFrameService;
		this.userIdentityResolver = userIdentityResolver;
		this.responseGenerator = responseGenerator;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	private ResponseEntity getPictureFrames(@RequestParam(value="page", defaultValue="0", required=false)
                                            final int page,
                                            @RequestParam(value="size", defaultValue = "10", required=false)
                                            final int size,
                                            final Authentication authentication) {
	    String userIdentity = userIdentityResolver.resolve(authentication);
		logger.debug(String.format("Page %d\tsize %d from %s requested.", page, size, userIdentity));
		try {
			if(page < 0 || size < 0) {
				return responseGenerator.generatePageRequestErrorResponse();
			}
			Collection<PictureFrame> pictureFrames = pictureFrameService.getAll(userIdentity, page, size);
            logger.trace(String.format(
                    "Responding page %d\tsize %d from %s requested. ---> %d",
                    page,
                    size,
                    userIdentity,
                    pictureFrames.size()
            ));
			return responseGenerator.generateResponse(pictureFrames);
		} catch (InvalidPictureFramePersistenceException | EmptyResultException e) {
            return handleUnexpectedError(e);
		}
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity getPictureFrame(@PathVariable("id")
                                           final Long id,
										   final Authentication authentication) {
        String userIdentity = userIdentityResolver.resolve(authentication);
        logger.debug(String.format("Picture frame %d from %s requested.", id, userIdentity));
		try {
			PictureFrame pictureFrame = pictureFrameService.getById(id, userIdentity);
            logger.trace(String.format(
                    "Responding picture frame %d from %s. ---> %s", id, userIdentity, pictureFrame.toString()
            ));
			return responseGenerator.generateResponse(pictureFrame);
		} catch (InvalidPictureFramePersistenceException | EmptyResultException e) {
            return handleUnexpectedError(e);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	private ResponseEntity savePictureFrame(@RequestBody
                                            PictureFrame pictureFrame,
											final Authentication authentication) {
        if(pictureFrame.getId() != null) {
            return responseGenerator.generateErrorResponse(
                    "invalid-request",
                    "The entity should not have an id.",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        String userIdentity = userIdentityResolver.resolve(authentication);
        logger.debug(String.format("Picture frame from %s post requested.", userIdentity));
		try {
			pictureFrame = pictureFrameService.persist(
					userIdentityResolver.resolve(authentication),
					pictureFrame
			);
            logger.trace(String.format(
                    "Picture frame %d from %s post request accepted. ---> %s",
                    pictureFrame.getId(),
                    userIdentity,
                    pictureFrame
            ));
			return responseGenerator.generateOkResponse();
		} catch (InvalidPictureFramePersistenceException e) {
            return handleUnexpectedError(e);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	private ResponseEntity updatePictureFrame(@RequestBody
                                              final PictureFrame pictureFrame,
											  final Authentication authentication) {
        if(pictureFrame.getId() == null) {
            return responseGenerator.generateErrorResponse(
                    "invalid-request",
                    "The entity should have an id.",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

		String userIdentity = userIdentityResolver.resolve(authentication);
        logger.debug(String.format("User %s updated picture frame with id %d", userIdentity, pictureFrame.getId()));
		try {
			pictureFrameService.persist(
                    userIdentity,
					pictureFrame
			);
            logger.debug(String.format(
                    "User %s updated picture frame with id %d accepted. ---> %s",
                    userIdentity,
                    pictureFrame.getId()));
			return responseGenerator.generateOkResponse();
		} catch (InvalidPictureFramePersistenceException e) {
            return handleUnexpectedError(e);
		}
	}

    private ResponseEntity handleUnexpectedError(final Exception e) {
        logger.error("There was an unexpected error while trying to get picture frames page.", e);
        return responseGenerator.generateInternalServerErrorResponse();
    }
}

package com.weframe.product.pictureframe.controller;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.controllers.errors.Error;
import com.weframe.product.generic.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.pictureframe.service.PictureFrameService;
import com.weframe.user.service.security.UserIdentityResolver;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

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
	private ResponseEntity getPictureFrames(
			@RequestParam(value="page", defaultValue="0", required = false) final int page,
			@RequestParam(value="size", defaultValue = "10", required = false) final int size,
			final Authentication authentication) {
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

		String userIdentity = userIdentityResolver.resolve(authentication);

		try {
			Collection<PictureFrame> pictureFrames = pictureFrameService.getAll(
					userIdentity,
					page,
					size
			);
			return responseGenerator.generateResponse(pictureFrames);
		} catch (InvalidGenericProductPersistenceException | EmptyResultException e) {
			Error error = new Error(
					"internal-server-error",
					"There has been an internal server error. Please try again later.");
			return responseGenerator.generateErrorResponse(
					Collections.singleton(error),
					HttpStatus.SERVICE_UNAVAILABLE
			);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity getPictureFrame(@PathVariable("id") final Long id,
										   final Authentication authentication) {
		String userIdentity = userIdentityResolver.resolve(authentication);

		try {
			PictureFrame pictureFrame = pictureFrameService.getById(
					id,
					userIdentity
			);
			return responseGenerator.generateResponse(pictureFrame);
		} catch (InvalidGenericProductPersistenceException | EmptyResultException e) {
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
	private ResponseEntity savePictureFrame(@RequestBody final PictureFrame pictureFrame,
											final Authentication authentication) {
		String userIdentity = userIdentityResolver.resolve(authentication);

		try {
			if(pictureFrame.getId() != null) {
				Error error = new Error(
						"invalid-request",
						"The entity should not have an id."
				);
				return responseGenerator.generateErrorResponse(
						Collections.singleton(error),
						HttpStatus.UNPROCESSABLE_ENTITY
				);
			}
			pictureFrameService.persist(pictureFrame);
			return responseGenerator.generateOkResponse();
		} catch (InvalidGenericProductPersistenceException e) {
			Error error = new Error(
					"internal-server-error",
					"There has been an internal server error. Please try again later.");
			return responseGenerator.generateErrorResponse(
					Collections.singleton(error),
					HttpStatus.SERVICE_UNAVAILABLE
			);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	private ResponseEntity updatePictureFrame(@RequestBody final PictureFrame pictureFrame,
											  final Authentication authentication) {
		String userIdentity = userIdentityResolver.resolve(authentication);

		try {
			if(pictureFrame.getId() == null) {
				Error error = new Error(
						"invalid-request",
						"The entity should have an id."
				);
				return responseGenerator.generateErrorResponse(
						Collections.singleton(error),
						HttpStatus.UNPROCESSABLE_ENTITY
				);
			}
			pictureFrameService.persist(pictureFrame);
			return responseGenerator.generateOkResponse();
		} catch (InvalidGenericProductPersistenceException e) {
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

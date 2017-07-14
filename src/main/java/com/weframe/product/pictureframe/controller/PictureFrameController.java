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
	private ResponseEntity getPictureFrames(
			@RequestParam(value="page", defaultValue="0", required = false) final int page,
			@RequestParam(value="size", defaultValue = "10", required = false) final int size,
			final Authentication authentication) {
		try {
			if(page < 0 || size < 0) {
				return responseGenerator.generatePageRequestErrorResponse();
			}

			Collection<PictureFrame> pictureFrames = pictureFrameService.getAll(
					userIdentityResolver.resolve(authentication),
					page,
					size
			);
			return responseGenerator.generateResponse(pictureFrames);
		} catch (InvalidPictureFramePersistenceException | EmptyResultException e) {
			return responseGenerator.generateInternalServerErrorResponse();
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity getPictureFrame(@PathVariable("id") final Long id,
										   final Authentication authentication) {
		try {
			PictureFrame pictureFrame = pictureFrameService.getById(
					id,
					userIdentityResolver.resolve(authentication)
			);
			return responseGenerator.generateResponse(pictureFrame);
		} catch (InvalidPictureFramePersistenceException | EmptyResultException e) {
			return responseGenerator.generateInternalServerErrorResponse();
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	private ResponseEntity savePictureFrame(@RequestBody final PictureFrame pictureFrame,
											final Authentication authentication) {
		try {
			if(pictureFrame.getId() != null) {
				return responseGenerator.generateErrorResponse(
						"invalid-request",
						"The entity should not have an id.",
						HttpStatus.UNPROCESSABLE_ENTITY
				);
			}
			pictureFrameService.persist(
					userIdentityResolver.resolve(authentication),
					pictureFrame
			);
			return responseGenerator.generateOkResponse();
		} catch (InvalidPictureFramePersistenceException e) {
			return responseGenerator.generateInternalServerErrorResponse();
		}
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	private ResponseEntity updatePictureFrame(@RequestBody final PictureFrame pictureFrame,
											  final Authentication authentication) {
		String userIdentity = userIdentityResolver.resolve(authentication);

		try {
			if(pictureFrame.getId() == null) {
				return responseGenerator.generateErrorResponse(
						"invalid-request",
						"The entity should have an id.",
						HttpStatus.UNPROCESSABLE_ENTITY
				);
			}
			pictureFrameService.persist(
					userIdentityResolver.resolve(authentication),
					pictureFrame
			);
			return responseGenerator.generateOkResponse();
		} catch (InvalidPictureFramePersistenceException e) {
			return responseGenerator.generateInternalServerErrorResponse();
		}
	}
}

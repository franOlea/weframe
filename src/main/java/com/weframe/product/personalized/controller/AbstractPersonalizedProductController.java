package com.weframe.product.personalized.controller;

import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.controllers.errors.Error;
import com.weframe.product.personalized.model.PictureFrameComponent;
import com.weframe.product.personalized.service.InvalidPersonalizedProductPersistenceException;
import com.weframe.product.personalized.service.PersonalizedProductService;
import com.weframe.user.service.security.UserIdentityResolver;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

@SuppressWarnings({"unused", "WeakerAccess"})
public class AbstractPersonalizedProductController<T extends PictureFrameComponent> {
	private static final Logger logger = Logger.getLogger(AbstractPersonalizedProductController.class);

	private final ResponseGenerator<T> responseGenerator;

	private final PersonalizedProductService<T> service;
	private final UserIdentityResolver userIdentityResolver;

	public AbstractPersonalizedProductController(final ResponseGenerator<T> responseGenerator,
												 final PersonalizedProductService<T> service,
												 final UserIdentityResolver userIdentityResolver) {
		this.responseGenerator = responseGenerator;
		this.service = service;
		this.userIdentityResolver = userIdentityResolver;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	private ResponseEntity getAllForUser(final Authentication authentication) {
		String userIdentity = userIdentityResolver.resolve(authentication);
		try {
			return responseGenerator.generateResponse(
					service.getAllByUserIdentity(userIdentity)
			);
		} catch (EmptyResultException e) {
			return responseGenerator.generateEmptyResponse();
		} catch (InvalidPersonalizedProductPersistenceException e) {
			return responseGenerator.generateErrorResponse(
					Collections.singleton(
							new Error(
									"Internal Server Error",
									"There was an internal server error, please try again later."
							)
					),
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity getById(@PathVariable final Long id,
								   final Authentication authentication) {
		String userIdentity = userIdentityResolver.resolve(authentication);
		try {
			return responseGenerator.generateResponse(
					service.getById(id, userIdentity)
			);
		} catch (EmptyResultException e) {
			return responseGenerator.generateEmptyResponse();
		} catch (InvalidPersonalizedProductPersistenceException e) {
			return responseGenerator.generateErrorResponse(
					Collections.singleton(
							new Error(
									"Internal Server Error",
									"There was an internal server error, please try again later."
							)
					),
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}


}

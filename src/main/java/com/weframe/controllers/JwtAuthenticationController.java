package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.controllers.errors.ErrorResponse;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.EmailAlreadyUsedException;
import com.weframe.user.service.persistence.exception.ForbiddenOperationException;
import com.weframe.user.service.persistence.exception.InvalidFieldException;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;
import com.weframe.user.service.security.UserIdentityResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/authentication")
@CrossOrigin
@Profile("jwt")
public class JwtAuthenticationController {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationController.class);

    private final ResponseGenerator<User> responseGenerator;

    private final UserService userService;
    private final UserIdentityResolver identityResolver;

    public JwtAuthenticationController(final ResponseGenerator<User> responseGenerator,
                                       final UserService userService,
                                       final UserIdentityResolver identityResolver) {
        this.responseGenerator = responseGenerator;
        this.userService = userService;
        this.identityResolver = identityResolver;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    private ResponseEntity getMe(final Authentication authentication) {
        try {
            String identityEmail = identityResolver.resolve(authentication);
            User user = userService.getByEmail(identityEmail);
            logger.info("Retrieved user [" + user + "] by token id [" + authentication.getName() + "]");
            return responseGenerator.generateResponse(user);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to fetch a user by token id [%s].",
                            authentication.getName()
                    ),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity generateAuthenticationError() {
        return responseGenerator.generateResponse(HttpStatus.I_AM_A_TEAPOT);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private ResponseEntity register(@RequestBody User user) {
        try {
            userService.create(user);
            logger.info("Created user [" + user.getEmail() + "].");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        } catch(InvalidFieldException e) {
            Error error = new Error(
                    String.format("invalid-%s", e.getField()),
                    String.format(
                            "The %s %s given is invalid, please try again.",
                            e.getField(),
                            e.getFieldContent()
                    )
            );
            return generateErrorResponse(Collections.singleton(error), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (EmailAlreadyUsedException e) {
            Error error = new Error("email-already-in-use", "The email given is already in use.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch(ForbiddenOperationException e) {
            Error error = new Error("invalid-user", "The user parameters given are invalid.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch(InvalidUserPersistenceException e) {
            logger.error(String.format("There has been an error creating user [%s]", user.getEmail()), e);
            Error error = new Error(
                    "internal-serer-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            logger.error("There was an unexpected error trying to create a user.", e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(final Collection<Error> errors,
                                                                final HttpStatus httpStatus) {
        return responseGenerator.generateErrorResponse(errors, httpStatus);
    }
}

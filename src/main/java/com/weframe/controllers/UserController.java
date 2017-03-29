package com.weframe.controllers;


import com.weframe.controllers.errors.ErrorResponse;
import com.weframe.controllers.errors.Error;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.EmailAlreadyUsedException;
import com.weframe.user.service.persistence.exception.EmptyResultException;
import com.weframe.user.service.persistence.exception.ForbiddenOperationException;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/users")
class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getUsers(
            @RequestParam(value="page", defaultValue="0",required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="email", required = false) final String email) {
        if(StringUtils.isBlank(email)) {
            return getUsersWithPaging(page, size);
        } else {
            return getUserByEmail(email);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody User user) {
        try {
            userService.create(user);
            logger.info("Created user [" + user.getEmail() + "].");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        } catch(EmailAlreadyUsedException e) {
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
                    "internal-serer-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    private ResponseEntity getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            logger.info("Retrieved user [" + user + "] by id [" + userId + "]");
            return generateResponse(user);
        } catch (EmptyResultException e) {
            return generateEmptyResponse();
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to fetch a user by id [%d].",
                            userId),
                    e);
            Error error = new Error(
                    "internal-serer-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    private ResponseEntity update(@RequestBody User user) {
        try {
            userService.update(user);
            logger.info("Updated user [" + getUserByEmail(user.getEmail()) + "].");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        } catch(ForbiddenOperationException e) {
            logger.error("There was an error trying to update a user.", e);
            Error error = new Error(
                    "invalid-user-data",
                    "The user data provided is not valid.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error("There was an unexpected error trying to update a user.", e);
            Error error = new Error(
                    "internal-serer-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long userId) {
        try {
            userService.delete(userId);
            logger.info("Deleted user [" + userId + "].");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(
                    String.format(
                            "There was an unexpected error trying to delete a user by id [%d].",
                            userId),
                    e);
            Error error = new Error(
                    "internal-serer-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity getUserByEmail(final String email) {
        try {
            User user = userService.getByEmail(email);
            return generateResponse(user);
        } catch (EmptyResultException e) {
            return generateEmptyResponse();
        } catch (InvalidUserPersistenceException e) {
            logger.error(String.format("There has been an error requesting user by email [%s]", email), e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity getUsersWithPaging(final int page, final int size) {
        try {
            Collection<User> users = userService.getAll(size, page);
            return generateResponse(users);
        } catch (EmptyResultException e) {
            return generateEmptyResponse();
        } catch (InvalidUserPersistenceException e) {
            logger.error(
                    String.format(
                            "There has been an error requesting users by page [%d] with size [%d]",
                            page,
                            size),
                    e);
            Error error = new Error(
                    "internal-server-error",
                    "There has been an internal server error. Please try again later.");
            return generateErrorResponse(Collections.singleton(error), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity generateEmptyResponse() {
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<User> generateResponse(final User user) {
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }

    private ResponseEntity<Collection<User>> generateResponse(final Collection<User> users) {
        return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(final Collection<Error> errors,
                                                                final HttpStatus httpStatus) {
        return new ResponseEntity<>(new ErrorResponse(errors), new HttpHeaders(), httpStatus);
    }

}

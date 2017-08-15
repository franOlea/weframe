package com.weframe.user.controller;


import com.weframe.controllers.EmptyResultException;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.controllers.errors.ErrorResponse;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.EmailAlreadyUsedException;
import com.weframe.user.service.persistence.exception.ForbiddenOperationException;
import com.weframe.user.service.persistence.exception.InvalidFieldException;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    private final UserService userService;
    private final ResponseGenerator<User> responseGenerator;

    public UserController(final UserService userService,
                          final ResponseGenerator<User> responseGenerator) {
        this.userService = userService;
        this.responseGenerator = responseGenerator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getUsers(
            @RequestParam(value="page", defaultValue="0", required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="email", required = false) final String email) {
        if(StringUtils.isBlank(email)) {
            if(page < 0 || size < 0) {
                responseGenerator.generatePageRequestErrorResponse();
            }
            return getUsersWithPaging(page, size);
        } else {
            return getUserByEmail(email);
        }
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    private ResponseEntity getCount() {
        try {
            return responseGenerator.generateCountResponse(userService.getUsersCount());
        } catch (InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody User user) {
        try {
            userService.create(user);
            logger.info("Created user [" + user.getEmail() + "].");
            return responseGenerator.generateOkResponse();
        } catch(InvalidFieldException e) {
            return generateInvalidFieldsError(e);
        } catch (EmailAlreadyUsedException e) {
            return generateEmailInUseError();
        } catch(ForbiddenOperationException e) {
            return generateInvalidParametersError();
        } catch(InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    private ResponseEntity getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            logger.info("Retrieved user [" + user + "] by id [" + userId + "]");
            return responseGenerator.generateResponse(user);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    private ResponseEntity update(@RequestBody User user) {
        try {
            userService.update(user);
            logger.info("Updated user [" + getUserByEmail(user.getEmail()) + "].");
            return responseGenerator.generateResponse(user);
        } catch(ForbiddenOperationException e) {
            return generateInvalidParametersError();
        } catch (InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long userId) {
        try {
            userService.delete(userId);
            logger.info("Deleted user [" + userId + "].");
            return responseGenerator.generateOkResponse();
        } catch(InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    private ResponseEntity getUserByEmail(final String email) {
        try {
            User user = userService.getByEmail(email);
            return responseGenerator.generateResponse(user);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    private ResponseEntity getUsersWithPaging(final int page, final int size) {
        try {
            Collection<User> users = userService.getAll(size, page);
            return responseGenerator.generateResponse(users);
        } catch (EmptyResultException e) {
            return responseGenerator.generateEmptyResponse();
        } catch (InvalidUserPersistenceException e) {
            return responseGenerator.generateInternalServerErrorResponse();
        }
    }

    private ResponseEntity generateInvalidParametersError() {
        return responseGenerator.generateErrorResponse(
                "invalid-user",
                "The user parameters given are invalid.",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    private ResponseEntity generateEmailInUseError() {
        return responseGenerator.generateErrorResponse(
                "email-already-in-use",
                "The email given is already in use.",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    private ResponseEntity<ErrorResponse> generateInvalidFieldsError(final InvalidFieldException e) {
        return responseGenerator.generateErrorResponse(
                String.format("invalid-%s", e.getField()),
                String.format(
                        "The %s %s given is invalid, please try again.",
                        e.getField(),
                        e.getFieldContent()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

}

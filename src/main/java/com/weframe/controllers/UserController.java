package com.weframe.controllers;


import com.weframe.user.UserPasswordCryptographer;
import com.weframe.user.UserValidator;
import com.weframe.user.model.User;
import com.weframe.user.service.RoleService;
import com.weframe.user.service.StateService;
import com.weframe.user.service.UserService;
import com.weframe.user.service.exception.InvalidUserPersistenceRequestException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/users")
class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StateService stateService;
    @Autowired
    private UserPasswordCryptographer passwordCryptographer;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getUsers(
            @RequestParam(value="offset", defaultValue="0",required = false) final int offset,
            @RequestParam(value="limit", defaultValue = "10", required = false) final int limit,
            @RequestParam(value="email", required = false) final String email) {
        try {
            if(email != null) {
                return successfulUnitRequest(getUserByEmail(email));
            }
            Collection<User> users = userService.getAllWithPaging(offset, limit);
            logger.info("Retrieved user list of size " +
                    "[" + users.size() + "] by limit [" + limit + "] and offset [" + offset + "].");
            return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
        } catch(InvalidUserPersistenceRequestException e) {
            return failedRequest(HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return failedRequest(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return failedRequest(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody User user) {
        try {
            user.setRole(roleService.getDefaultRole());
            user.setState(stateService.getDefaultState());
            user.setPassword(passwordCryptographer.generateStoringPasswordHash(user.getPassword()));
            if(userValidator.isValidInsert(user)) {
                userService.insert(user);
                logger.info("Created user [" + user.getEmail() + "].");
                return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
            } else {
                throw new InvalidUserPersistenceRequestException();
            }
        } catch(InvalidUserPersistenceRequestException e) {
            return failedRequest(HttpStatus.BAD_REQUEST);
        } catch(DataIntegrityViolationException e) {
            return failedRequest(HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(user.toString(), e);
            return failedRequest(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    private ResponseEntity update(@RequestBody User user) {
        try {
            if(userValidator.isValidUpdate(user)) {
                userService.update(user);
                logger.info("Updated user [" + getUserByEmail(user.getEmail()) + "].");
                return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
            } else {
                throw new InvalidUserPersistenceRequestException();
            }
        } catch(InvalidUserPersistenceRequestException e) {
            return failedRequest(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e);
            return failedRequest(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    private ResponseEntity getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            if(user == null) {
                throw new EmptyResultDataAccessException(1);
            }
            logger.info("Retrieved user [" + user + "] by id [" + userId + "]");
            return successfulUnitRequest(user);
        } catch(InvalidUserPersistenceRequestException e) {
            return failedRequest(HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return failedRequest(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return failedRequest(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long userId) {
        try {
            userService.deleteById(userId);
            logger.info("Deleted user [" + userId + "].");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        } catch(InvalidUserPersistenceRequestException e) {
            return failedRequest(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e);
            return failedRequest(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity successfulUnitRequest(User user) {
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }

    private ResponseEntity successfulCollectionRequest(Collection<User> users) {
        return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
    }

    private ResponseEntity failedRequest(HttpStatus httpStatus) {
        return new ResponseEntity<>(null, new HttpHeaders(), httpStatus);
    }

    private User getUserByEmail(String email) {
        User user = userService.getByEmail(email);

        if(user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        logger.info("Retrieved user [" + user + "] by email [" + email + "]");
        return user;
    }

}

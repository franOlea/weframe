package com.weframe.controllers;


import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.EmptyResultException;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity getUsers(
            @RequestParam(value="page", defaultValue="0",required = false) final int page,
            @RequestParam(value="size", defaultValue = "10", required = false) final int size,
            @RequestParam(value="email", required = false) final String email) {
        try {
            ResponseEntity responseEntity;
            if(!StringUtils.isBlank(email)) {
                User user = userService.getByEmail(email);
                return generateResponse(user);
            } else {
                Collection<User> users = userService.getAll(size, page);
                return generateResponse(users);
            }
        } catch (EmptyResultException e) {
            e.printStackTrace();
        } catch (InvalidUserPersistenceException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity create(@RequestBody User user) {
        try {
            user.setRole(roleRepository.getDefaultRole());
            user.setState(stateRepository.getDefaultState());
            user.setPassword(passwordCryptographer.generateStoringPasswordHash(user.getPassword()));
            if(userValidator.isValidInsert(user)) {
                userRepository.insert(user);
                logger.info("Created user [" + user.getEmail() + "].");
                return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
            } else {
                throw new InvalidUserPersistenceException();
            }
        } catch(InvalidUserPersistenceException e) {
            return generateResponseError(HttpStatus.BAD_REQUEST);
        } catch(DataIntegrityViolationException e) {
            return generateResponseError(HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(user.toString(), e);
            return generateResponseError(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    private ResponseEntity update(@RequestBody User user) {
        try {
            if(userValidator.isValidUpdate(user)) {
                userRepository.update(user);
                logger.info("Updated user [" + getUserByEmail(user.getEmail()) + "].");
                return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
            } else {
                throw new InvalidUserPersistenceException();
            }
        } catch(InvalidUserPersistenceException e) {
            return generateResponseError(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e);
            return generateResponseError(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    private ResponseEntity getUserById(@PathVariable Long userId) {
        try {
            User user = userRepository.getById(userId);
            if(user == null) {
                throw new EmptyResultDataAccessException(1);
            }
            logger.info("Retrieved user [" + user + "] by id [" + userId + "]");
            return successfulUnitRequest(user);
        } catch(InvalidUserPersistenceException e) {
            return generateResponseError(HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return generateResponseError(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return generateResponseError(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    private ResponseEntity delete(@PathVariable Long userId) {
        try {
            userRepository.deleteById(userId);
            logger.info("Deleted user [" + userId + "].");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        } catch(InvalidUserPersistenceException e) {
            return generateResponseError(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e);
            return generateResponseError(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity generateResponse(User user) {
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }

    private ResponseEntity generateResponse(Collection<User> users) {
        return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
    }


}

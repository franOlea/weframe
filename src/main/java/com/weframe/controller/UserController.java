package com.weframe.controller;


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

@RestController
@RequestMapping("/users/")
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StateService stateService;

    @RequestMapping(value = "/by-id/{userId}", method = RequestMethod.GET)
    ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);

            if(user == null) {
                throw new EmptyResultDataAccessException(1);
            }

            logger.info("Retrieved user [" + user + "] by id [" + userId + "]");
            return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.FOUND);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/by-email/{email:.+}", method = RequestMethod.GET)
    ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getByEmail(email);

            if(user == null) {
                throw new EmptyResultDataAccessException(1);
            }

            logger.info("Retrieved user [" + user + "] by email [" + email + "]");
            return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.FOUND);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/get-all-with-paging", method = RequestMethod.GET)
    ResponseEntity<Collection<User>> getAllUsersWithPaging(@RequestParam(value="offset", defaultValue="0",
            required = false) int offset, @RequestParam(value="limit", defaultValue = "10") int limit) {
        try {
            Collection<User> users = userService.getAllWithPaging(offset, limit);
            logger.info("Retrieved user list of size " +
                    "[" + users.size() + "] by limit [" + limit + "] and offset [" + offset + "].");

            return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.FOUND);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody User user) {
        try {
            user.setRole(roleService.getDefaultRole());
            user.setState(stateService.getDefaultState());

            userService.insert(user);
            logger.info("Created user [" + user.getEmail() + "].");

            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CREATED);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch(DataIntegrityViolationException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(user.toString(), e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    ResponseEntity<?> update(@RequestBody User user) {
        try {
            userService.update(user);
            logger.info("Updated user [" + getUserByEmail(user.getEmail()) + "].");

            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.ACCEPTED);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

}

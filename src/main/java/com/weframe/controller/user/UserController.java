package com.weframe.controller.user;


import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private UserDao userDao;

    @RequestMapping(value = "/by-id/{userId}", method = RequestMethod.GET)
    ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userDao.getById(userId);

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

    @RequestMapping(value = "/by-email/{userId}", method = RequestMethod.GET)
    ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userDao.getByEmail(email);

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
            required = false) int offset, @RequestParam(value="limit") int limit) {
        try {
            Collection<User> users = userDao.getAllWithPaging(offset, limit);

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
            userDao.insert(user);

            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CREATED);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch(DuplicateKeyException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    ResponseEntity<?> update(@RequestBody User user) {
        try {
            userDao.update(user);

            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.ACCEPTED);
        } catch(InvalidUserPersistenceRequestException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

}

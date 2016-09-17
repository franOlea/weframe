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

}

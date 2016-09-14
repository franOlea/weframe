package com.weframe.controller.user;


import com.weframe.model.user.User;
import com.weframe.service.user.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @RequestMapping(value = "/byId/{userId}", method = RequestMethod.GET)
    private ResponseEntity<User> getUserById(@PathVariable Long userId) {
        if(userId < 1) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        try {
            User user = userDao.getById(userId);
            user.setRole(null);
            user.setPassword(null);
            user.setPasswordSalt(null);

            return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.FOUND);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private ResponseEntity<?> create(@RequestBody User user) {
        if(!isValidUser(user)) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        try {
            userDao.insert(user);

            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CREATED);
        } catch(DuplicateKeyException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    private static boolean isValidUser(User user) {
        return user != null &&
                user.getId() > 0 &&
                user.getEmail() != null &&
                user.getFirstName() != null &&
                user.getLastName() != null &&
                user.getPassword() != null &&
                user.getPasswordSalt() != null &&
                user.getRole() != null &&
                user.getRole().getId() > 0 &&
                user.getRole().getName() != null;
    }

}

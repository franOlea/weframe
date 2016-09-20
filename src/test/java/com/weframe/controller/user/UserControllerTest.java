package com.weframe.controller.user;

import com.weframe.model.user.User;
import com.weframe.model.user.fixture.UserFixture;
import com.weframe.service.user.UserDao;
import com.weframe.service.user.exception.InvalidUserPersistenceRequestException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


@RunWith(JMockit.class)
public class UserControllerTest {

    @Injectable
    private UserDao userDao;

    @Tested
    private UserController userController = new UserController();

    @Test
    public void getByIdBadRequest() throws Exception {
        new Expectations() {{
            userDao.getById(-1L);
            result = new InvalidUserPersistenceRequestException();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserById(-1L);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void getByIdDataBaseError() throws Exception {
        new Expectations() {{
            userDao.getById(1L);
            result = new DataIntegrityViolationException("Error");
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserById(1L);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        new Expectations() {{
            userDao.getById(1L);
            result = new EmptyResultDataAccessException(1);
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserById(1L);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getByIdFound() throws Exception {
        new Expectations() {{
            userDao.getById(1L);
            result = UserFixture.johnDoe();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserById(1L);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assert.assertEquals(UserFixture.johnDoe(), responseEntity.getBody());
    }

    @Test
    public void getByEmailBadRequest() throws Exception {
        new Expectations() {{
            userDao.getByEmail("");
            result = new InvalidUserPersistenceRequestException();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserByEmail("");

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void getByEmailDataBaseError() throws Exception {
        new Expectations() {{
            userDao.getByEmail("test@email.com");
            result = new DataIntegrityViolationException("Error");
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserByEmail("test@email.com");

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void getUserByEmailNotFound() throws Exception {
        new Expectations() {{
            userDao.getByEmail("john.doe@email.com");
            result = new EmptyResultDataAccessException(1);
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserByEmail("john.doe@email.com");

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getByEmailFound() throws Exception {
        new Expectations() {{
            userDao.getByEmail("john.doe@email.com");
            result = UserFixture.johnDoe();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getUserByEmail("john.doe@email.com");

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assert.assertEquals(UserFixture.johnDoe(), responseEntity.getBody());
    }

    @Test
    public void getAllWithPagingBadRequest() throws Exception {
        new Expectations() {{
            userDao.getAllWithPaging(-1, -1);
            result = new InvalidUserPersistenceRequestException();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getAllUsersWithPaging(-1, -1);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void getAllWithPagingDataBaseError() throws Exception {
        new Expectations() {{
            userDao.getAllWithPaging(1, 1);
            result = new DataIntegrityViolationException("Error");
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getAllUsersWithPaging(1, 1);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void getAllWithPagingNotFound() throws Exception {
        new Expectations() {{
            userDao.getAllWithPaging(1, 1);
            result = new EmptyResultDataAccessException(1);
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.getAllUsersWithPaging(1, 1);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAllWithPagingFound() throws Exception {
        new Expectations() {{
            userDao.getAllWithPaging(0, 2);
            result = new ArrayList<>(Arrays.asList(UserFixture.janeDoe(), UserFixture.johnDoe()));
            times = 1;
        }};

        ResponseEntity<Collection<User>> responseEntity = userController.getAllUsersWithPaging(0, 2);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assert.assertEquals(
                new ArrayList<>(Arrays.asList(UserFixture.janeDoe(), UserFixture.johnDoe())),
                responseEntity.getBody());
    }

    @Test
    public void insertBadRequest() {
        User user = UserFixture.janeDoe();
        user.setFirstName(null);

        new Expectations() {{
            userDao.insert(user);
            result = new InvalidUserPersistenceRequestException();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.create(user);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void insertCreated() {
        User user = new User(
                4,
                UserFixture.janeDoe().getFirstName(),
                UserFixture.janeDoe().getLastName(),
                UserFixture.janeDoe().getEmail(),
                UserFixture.janeDoe().getPassword(),
                UserFixture.janeDoe().getPasswordSalt(),
                UserFixture.janeDoe().getRole());

        new Expectations() {{
            userDao.insert(user);
            result = null;
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.create(user);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void insertConflict() {
        User user = UserFixture.johnDoe();

        new Expectations() {{
            userDao.insert(user);
            result = new DuplicateKeyException("Error");
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.create(user);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void insertDataBaseError() throws Exception {
        User user = UserFixture.johnDoe();

        new Expectations() {{
            userDao.insert(user);
            result = new DataIntegrityViolationException("Error");
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.create(user);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void updateBadRequest() {
        User user = UserFixture.janeDoe();
        user.setFirstName(null);

        new Expectations() {{
            userDao.update(user);
            result = new InvalidUserPersistenceRequestException();
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.update(user);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void updateDataBaseError() {
        User user = UserFixture.janeDoe();
        user.setFirstName(null);

        new Expectations() {{
            userDao.update(user);
            result = new DataIntegrityViolationException("Error");
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.update(user);

        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
    }

    @Test
    public void updateAccepted() {
        User user = UserFixture.janeDoe();
        user.setFirstName(null);

        new Expectations() {{
            userDao.update(user);
            result = null;
            times = 1;
        }};

        ResponseEntity<?> responseEntity = userController.update(user);

        Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

}

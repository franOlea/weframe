package com.weframe.controller.user;

import com.weframe.model.user.User;
import com.weframe.model.user.fixture.UserFixture;
import com.weframe.service.user.UserDao;
import com.weframe.service.user.exception.InvalidUserException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@ActiveProfiles("embedded")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getByIdBadRequest() throws Exception {
        doThrow(new InvalidUserException()).when(this.userDao).getById(-10);

        ResponseEntity<?> responseEntity = this.restTemplate.getForEntity(
                "/users/by-id/-10",
                ResponseEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getByIdDataBaseError() throws Exception {
        doThrow(new DataIntegrityViolationException("Error")).when(this.userDao).getById(11);

        ResponseEntity<?> responseEntity = this.restTemplate.getForEntity(
                "/users/by-id/11",
                ResponseEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(this.userDao).getById(12);

        ResponseEntity<?> responseEntity = this.restTemplate.getForEntity(
                "/users/by-id/12",
                ResponseEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getByIdFound() throws Exception {
        given(this.userDao.getById(UserFixture.johnDoe().getId())).willReturn(UserFixture.johnDoe());

        User expectedUser = UserFixture.johnDoe();

        ResponseEntity<User> responseEntity = this.restTemplate.getForEntity(
                "/users/by-id/"+expectedUser.getId(),
                User.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        Assert.assertEquals(expectedUser.getId(), responseEntity.getBody().getId());
        Assert.assertEquals(expectedUser.getEmail(), responseEntity.getBody().getEmail());
        Assert.assertEquals(expectedUser.getFirstName(), responseEntity.getBody().getFirstName());
        Assert.assertEquals(expectedUser.getLastName(), responseEntity.getBody().getLastName());
        Assert.assertEquals(responseEntity.getBody().getPassword(), null);
        Assert.assertEquals(responseEntity.getBody().getPasswordSalt(), null);
        Assert.assertEquals(responseEntity.getBody().getRole(), null);
    }

    @Test
    public void insertBadRequest() {
        User user = UserFixture.janeDoe();
        user.setPassword(null);
        user.setPasswordSalt(null);
        user.setRole(null);

        doThrow(new InvalidUserException()).when(this.userDao).insert(user);

        ResponseEntity<?> responseEntity =
                this.restTemplate.postForEntity("/users/create", user, ResponseEntity.class);

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

        ResponseEntity<?> responseEntity =
                this.restTemplate.postForEntity("/users/create", user, ResponseEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void insertConflict() {
        User user = UserFixture.johnDoe();

        doThrow(new DuplicateKeyException("Error")).when(this.userDao).insert(user);

        ResponseEntity<?> responseEntity =
                this.restTemplate.postForEntity("/users/create", user, ResponseEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void insertDataBaseError() {
        User user = UserFixture.janeDoe();

        doThrow(new DataIntegrityViolationException("Error")).when(this.userDao).insert(user);

        ResponseEntity<?> responseEntity =
                this.restTemplate.postForEntity("/users/create", user, ResponseEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}

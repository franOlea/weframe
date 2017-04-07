package com.weframe.controllers;

import com.weframe.user.fixture.UserFixture;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.exception.EmptyResultException;
import com.weframe.user.service.persistence.exception.InvalidUserPersistenceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getPersistedUserById() throws Exception {
        User user = UserFixture.getDefaultPersistedUser();

        when(userService.getById(user.getId())).thenReturn(user);

        this.mockMvc.perform(get("/users/"+user.getId().intValue()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.role.id", is(user.getRole().getId().intValue())))
                .andExpect(jsonPath("$.role.name", is(user.getRole().getName())))
                .andExpect(jsonPath("$.state.id", is(user.getState().getId().intValue())))
                .andExpect(jsonPath("$.state.name", is(user.getState().getName())));
    }

    @Test
    public void getUserByIdWithEmptyResponse() throws Exception {
        when(userService.getById(1L)).thenThrow(new EmptyResultException());

        this.mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getUserByIdWithInternalServerError() throws Exception {
        when(userService.getById(1L)).thenThrow(new InvalidUserPersistenceException());

        this.mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].title", is("internal-server-error")))
                .andExpect(jsonPath("$.errors[0].detail",
                        is("There has been an internal server error. Please try again later."))
                );
    }

    @Test
    public void getPersistedUserByEmail() throws Exception {
        User user = UserFixture.getDefaultPersistedUser();

        when(userService.getByEmail(user.getEmail())).thenReturn(user);

        this.mockMvc.perform(get("/users?email="+user.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.role.id", is(user.getRole().getId().intValue())))
                .andExpect(jsonPath("$.role.name", is(user.getRole().getName())))
                .andExpect(jsonPath("$.state.id", is(user.getState().getId().intValue())))
                .andExpect(jsonPath("$.state.name", is(user.getState().getName())));
    }

    @Test
    public void getUserByEmailWithEmptyResponse() throws Exception {
        when(userService.getByEmail("email@address.com")).thenThrow(new EmptyResultException());

        this.mockMvc.perform(get("/users?email=email@address.com"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getUserByEmailWithInternalServerError() throws Exception {
        when(userService.getByEmail("email@address.com")).thenThrow(new InvalidUserPersistenceException());

        this.mockMvc.perform(get("/users?email=email@address.com"))
                .andDo(print())
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].title", is("internal-server-error")))
                .andExpect(jsonPath("$.errors[0].detail",
                        is("There has been an internal server error. Please try again later."))
                );
    }
}

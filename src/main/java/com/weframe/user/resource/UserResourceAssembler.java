package com.weframe.user.resource;

import com.weframe.controller.UserController;
import com.weframe.user.model.User;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        UserResource userResource = createResourceWithId(user.getId(), user);
        userResource.setId(user.getId());
        userResource.setEmail(user.getEmail());
        userResource.setPassword(user.getPassword());
        userResource.setFirstName(user.getFirstName());
        userResource.setLastName(user.getLastName());
        userResource.setRole(user.getRole());
        userResource.setState(user.getState());

        return userResource;
    }
}

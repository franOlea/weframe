package com.weframe.user.resource;

import com.weframe.user.model.Role;
import com.weframe.user.model.State;
import org.apache.commons.lang3.Validate;
import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private State state;

    public UserResource() { }

    public UserResource(final long id,
                final String firstName,
                final String lastName,
                final String email,
                final String password,
                final Role role,
                final State state) {
        Validate.isTrue(id >= 0, "The id cannot be negative");
        Validate.notBlank(firstName, "The first name cannot be blank");
        Validate.notBlank(lastName, "The last name cannot be blank");
        Validate.notBlank(email, "The email cannot be blank");
        Validate.notBlank(password, "The password cannot be blank");
        Validate.notNull(role, "The role cannot be blank");
        Validate.notNull(state, "The state cannot be null.");

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public State getState() {
        return state;
    }

    public void setId(final Long id) {
        Validate.isTrue(id > 0, "The id should be above 0");

        this.id = id;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserResource userResource = (UserResource) o;

        if (!id.equals(userResource.id)) return false;
        if (!firstName.equals(userResource.firstName)) return false;
        if (!lastName.equals(userResource.lastName)) return false;
        if (!email.equals(userResource.email)) return false;
        if (!password.equals(userResource.password)) return false;
        if (!role.equals(userResource.role)) return false;
        return state.equals(userResource.state);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", state=" + state +
                '}';
    }
}

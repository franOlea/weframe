package com.weframe.model.user;

import org.apache.commons.lang3.Validate;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordSalt;
    private Role role;

    public User() { }

    public User(final long id,
                final String firstName,
                final String lastName,
                final String email,
                final String password,
                final String passwordSalt,
                final Role role) {
        Validate.isTrue(id >= 0, "The id cannot be negative");
        Validate.notBlank(firstName, "The first name cannot be blank");
        Validate.notBlank(lastName, "The last name cannot be blank");
        Validate.notBlank(email, "The email cannot be blank");
        Validate.notBlank(password, "The password cannot be blank");
        Validate.notBlank(passwordSalt, "The password salt cannot be blank");
        Validate.notNull(role, "The role cannot be blank");

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.role = role;
    }

    public long getId() {
        return id;
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

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public Role getRole() {
        return role;
    }

    public void setId(final long id) {
        Validate.isTrue(id >= 0, "The id cannot be negative");

        this.id = id;
    }

    public void setFirstName(String firstName) {
        Validate.notBlank(firstName, "The first name cannot be blank");

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        Validate.notBlank(lastName, "The last name cannot be blank");

        this.lastName = lastName;
    }

    public void setEmail(String email) {
        Validate.notBlank(email, "The email cannot be blank");

        this.email = email;
    }

    public void setPassword(String password) {
        Validate.notBlank(password, "The password cannot be blank");

        this.password = password;
    }

    public void setPasswordSalt(String passwordSalt) {
        Validate.notBlank(passwordSalt, "The password salt cannot be blank");

        this.passwordSalt = passwordSalt;
    }

    public void setRole(Role role) {
        Validate.notNull(role, "The role cannot be blank");

        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (!passwordSalt.equals(user.passwordSalt)) return false;

        return role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = result + firstName.hashCode();
        result = result + lastName.hashCode();
        result = result + email.hashCode();
        result = result + password.hashCode();
        result = result + passwordSalt.hashCode();
        result = result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}

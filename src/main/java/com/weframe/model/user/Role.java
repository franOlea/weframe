package com.weframe.model.user;

import org.apache.commons.lang3.Validate;

public class Role {
    private long id;
    private String name;

    public Role() { }

    public Role(final long id, final String name) {
        Validate.isTrue(id >= 0, "The id cannot be negative");
        Validate.notEmpty(name, "The name cannot be empty");

        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(final long id) {

        this.id = id;
    }

    public void setName(final String name) {

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;

        return name.equals(role.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = result + name.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

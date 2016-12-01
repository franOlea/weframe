package com.weframe.user.model;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "STATES")
public class State {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public State() { }

    public State(final long id, final String name) {
        Validate.isTrue(id >= 0, "The id cannot be negative");
        Validate.notEmpty(name, "The name cannot be empty");

        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (id != state.id) return false;

        return name.equals(state.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = result + name.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

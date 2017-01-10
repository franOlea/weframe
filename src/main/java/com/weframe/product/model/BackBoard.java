package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "BACK_BOARDS")
public class BackBoard {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "COLOR", nullable = false)
    private String color;

    public BackBoard() {
    }

    public BackBoard(final Long id,
                     final String name,
                     final String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BackBoard backBoard = (BackBoard) o;

        if (!id.equals(backBoard.id)) return false;
        if (!name.equals(backBoard.name)) return false;
        return color.equals(backBoard.color);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BackBoard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

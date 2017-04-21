package com.weframe.product.model.generic;

import com.weframe.product.model.Picture;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "BACK_BOARDS")
public class BackBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "UNIQUE_NAME", nullable = false, unique = true)
    private String uniqueName;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @Column(name= "M2_PRICE", nullable = false)
    private float m2Price;

    public BackBoard() {
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPicture(final Picture picture) {
        this.picture = picture;
    }

    public void setM2Price(final float m2Price) {
        this.m2Price = m2Price;
    }
}

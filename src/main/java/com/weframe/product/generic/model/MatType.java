package com.weframe.product.generic.model;

import com.weframe.picture.model.Picture;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "MAT_TYPES")
public class MatType extends GenericProduct {

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @Column(name = "M2_PRICE", nullable = false)
    private float m2Price;

    public MatType() {
    }

    public MatType(final String name,
                   final String uniqueName,
                   final String description,
                   final Picture picture,
                   final float m2Price) {
        super(
                name,
                uniqueName,
                description
        );
        this.picture = picture;
        this.m2Price = m2Price;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(final Picture picture) {
        this.picture = picture;
    }

    public float getM2Price() {
        return m2Price;
    }

    public void setM2Price(final float m2Price) {
        this.m2Price = m2Price;
    }

}

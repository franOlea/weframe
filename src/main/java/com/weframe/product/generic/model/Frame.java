package com.weframe.product.generic.model;

import com.weframe.picture.model.Picture;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "FRAMES")
public class Frame extends GenericProduct {

    @Column(name = "HEIGHT", nullable = false)
    private float height;

    @Column(name = "LENGTH", nullable = false)
    private float length;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @Column(name= "PRICE", nullable = false)
    private float price;


    public Frame() {
    }

    public Frame(final String name,
                 final String uniqueName,
                 final String description,
                 final float height,
                 final float length,
                 final Picture picture,
                 final float price) {
        super(
                name,
                uniqueName,
                description
        );
        this.height = height;
        this.length = length;
        this.picture = picture;
        this.price = price;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(final float length) {
        this.length = length;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(final Picture picture) {
        this.picture = picture;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }
}

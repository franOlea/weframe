package com.weframe.product.generic.model;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "FRAME_GLASSES")
public class FrameGlass extends GenericProduct {

    @Column(name = "M2_PRICE", nullable = false)
    private float m2Price;

    public FrameGlass() {
    }

    public FrameGlass(final String name,
                      final String uniqueName,
                      final String description,
                      final float m2Price) {
        super(
                name,
                uniqueName,
                description
        );
        this.m2Price = m2Price;
    }

    public float getM2Price() {
        return m2Price;
    }

    public void setM2Price(final float m2Price) {
        this.m2Price = m2Price;
    }

}

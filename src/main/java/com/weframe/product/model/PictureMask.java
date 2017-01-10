package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "PICTURE_MASKS")
public class PictureMask {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "X_OFFSET", nullable = false)
    private float xOffset;
    @Column(name = "LENGTH", nullable = false)
    private float lenght;
    @Column(name = "Y_OFFSET", nullable = false)
    private float yOffset;
    @Column(name = "HEIGHT", nullable = false)
    private float height;

    public PictureMask() {
    }

    public PictureMask(final Long id,
                       final float xOffset,
                       final float lenght,
                       final float yOffset,
                       final float height) {
        this.id = id;
        this.xOffset = xOffset;
        this.lenght = lenght;
        this.yOffset = yOffset;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(final float xOffset) {
        this.xOffset = xOffset;
    }

    public float getLenght() {
        return lenght;
    }

    public void setLenght(final float lenght) {
        this.lenght = lenght;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(final float yOffset) {
        this.yOffset = yOffset;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PictureMask that = (PictureMask) o;

        if (Float.compare(that.xOffset, xOffset) != 0) return false;
        if (Float.compare(that.lenght, lenght) != 0) return false;
        if (Float.compare(that.yOffset, yOffset) != 0) return false;
        if (Float.compare(that.height, height) != 0) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (xOffset != +0.0f ? Float.floatToIntBits(xOffset) : 0);
        result = 31 * result + (lenght != +0.0f ? Float.floatToIntBits(lenght) : 0);
        result = 31 * result + (yOffset != +0.0f ? Float.floatToIntBits(yOffset) : 0);
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PictureMask{" +
                "id=" + id +
                ", xOffset=" + xOffset +
                ", lenght=" + lenght +
                ", yOffset=" + yOffset +
                ", height=" + height +
                '}';
    }
}

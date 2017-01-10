package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "LOCATED_PICTURES")
public class LocatedPicture {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;
    @ManyToOne
    @JoinColumn(name = "PICTURE_MASK", nullable = false)
    private PictureMask pictureMask;
    @Column(name = "LENGTH", nullable = false)
    private float length;
    @Column(name = "HEIGHT", nullable = false)
    private float height;
    @Column(name = "X_POSITION", nullable = false)
    private float xPosition;
    @Column(name = "Y_POSITION", nullable = false)
    private float yPosition;
    @Column(name = "Z_INDEX", nullable = false)
    private int zIndex;

    public LocatedPicture() {
    }

    public LocatedPicture(final Long id,
                          final Picture picture,
                          final PictureMask pictureMask,
                          final float length,
                          final float height,
                          final float xPosition,
                          final float yPosition,
                          final int zIndex) {
        this.id = id;
        this.picture = picture;
        this.pictureMask = pictureMask;
        this.length = length;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zIndex = zIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(final Picture picture) {
        this.picture = picture;
    }

    public PictureMask getPictureMask() {
        return pictureMask;
    }

    public void setPictureMask(final PictureMask pictureMask) {
        this.pictureMask = pictureMask;
    }

    public float getLength() {
        return length;
    }

    public void setLength(final float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(final float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(final float yPosition) {
        this.yPosition = yPosition;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(final int zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocatedPicture that = (LocatedPicture) o;

        if (Float.compare(that.length, length) != 0) return false;
        if (Float.compare(that.height, height) != 0) return false;
        if (Float.compare(that.xPosition, xPosition) != 0) return false;
        if (Float.compare(that.yPosition, yPosition) != 0) return false;
        if (zIndex != that.zIndex) return false;
        if (!id.equals(that.id)) return false;
        if (!picture.equals(that.picture)) return false;
        return pictureMask.equals(that.pictureMask);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + picture.hashCode();
        result = 31 * result + pictureMask.hashCode();
        result = 31 * result + (length != +0.0f ? Float.floatToIntBits(length) : 0);
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        result = 31 * result + (xPosition != +0.0f ? Float.floatToIntBits(xPosition) : 0);
        result = 31 * result + (yPosition != +0.0f ? Float.floatToIntBits(yPosition) : 0);
        result = 31 * result + zIndex;
        return result;
    }

    @Override
    public String toString() {
        return "LocatedPicture{" +
                "id=" + id +
                ", picture=" + picture +
                ", pictureMask=" + pictureMask +
                ", length=" + length +
                ", height=" + height +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", zIndex=" + zIndex +
                '}';
    }
}

package com.weframe.product.personalized.model;

import com.weframe.product.pictureframe.PictureFrame;

import javax.persistence.*;

@SuppressWarnings({"unused"})
@Entity
@Table(name = "PICTURE_MASKS")
public class PictureMask extends PictureFrameComponent{

    @Column(name = "X_OFFSET", nullable = false)
    private float xOffset;

    @Column(name = "LENGTH", nullable = false)
    private float length;

    @Column(name = "Y_OFFSET", nullable = false)
    private float yOffset;

    @Column(name = "HEIGHT", nullable = false)
    private float height;

    public PictureMask() {
    }

    public PictureMask(final Long id,
                       final PictureFrame pictureFrame,
                       final float xOffset,
                       final float length,
                       final float yOffset,
                       final float height) {
        super(id, pictureFrame);
        this.xOffset = xOffset;
        this.length = length;
        this.yOffset = yOffset;
        this.height = height;
    }

    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(final float xOffset) {
        this.xOffset = xOffset;
    }

    public float getLength() {
        return length;
    }

    public void setLength(final float length) {
        this.length = length;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(final float yOffset) {
        this.yOffset = yOffset;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }
}

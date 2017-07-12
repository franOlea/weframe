package com.weframe.product.personalized.model;

import com.weframe.picture.model.UserPicture;
import com.weframe.product.pictureframe.PictureFrame;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "LOCATED_PICTURES")
public class LocatedPicture extends PictureFrameComponent {

    @ManyToOne
    @JoinColumn(name = "USER_PICTURE", nullable = false)
    private UserPicture userPicture;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
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
                          final PictureFrame pictureFrame,
                          final UserPicture userPicture,
                          final PictureMask pictureMask,
                          final float length,
                          final float height,
                          final float xPosition,
                          final float yPosition,
                          final int zIndex) {
        super(id, pictureFrame);
        this.userPicture = userPicture;
        this.pictureMask = pictureMask;
        this.length = length;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zIndex = zIndex;
    }

    public UserPicture getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(final UserPicture userPicture) {
        this.userPicture = userPicture;
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
}

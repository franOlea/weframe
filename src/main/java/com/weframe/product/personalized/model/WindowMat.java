package com.weframe.product.personalized.model;

import com.weframe.product.generic.model.MatType;
import com.weframe.product.pictureframe.PictureFrame;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "WINDOW_MATS")
public class WindowMat extends PictureFrameComponent {

    @ManyToOne
    @JoinColumn(name = "MAT_TYPE", nullable = false)
    private MatType matType;

    @Column(name = "INNER_HORIZONTAL_BEZEL", nullable = false)
    private float innerHorizontalBezel;

    @Column(name = "INNER_VERTICAL_BEZEL", nullable = false)
    private float innerVerticalBezel;

    public WindowMat() {
    }

    public WindowMat(final Long id,
                     final PictureFrame pictureFrame,
                     final MatType matType,
                     final float innerHorizontalBezel,
                     final float innerVerticalBezel) {
        super(id, pictureFrame);
        this.matType = matType;
        this.innerHorizontalBezel = innerHorizontalBezel;
        this.innerVerticalBezel = innerVerticalBezel;
    }

    public MatType getMatType() {
        return matType;
    }

    public void setMatType(final MatType matType) {
        this.matType = matType;
    }

    public float getInnerHorizontalBezel() {
        return innerHorizontalBezel;
    }

    public void setInnerHorizontalBezel(final float innerHorizontalBezel) {
        this.innerHorizontalBezel = innerHorizontalBezel;
    }

    public float getInnerVerticalBezel() {
        return innerVerticalBezel;
    }

    public void setInnerVerticalBezel(final float innerVerticalBezel) {
        this.innerVerticalBezel = innerVerticalBezel;
    }
}

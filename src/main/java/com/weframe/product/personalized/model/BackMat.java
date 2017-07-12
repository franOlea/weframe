package com.weframe.product.personalized.model;

import com.weframe.product.generic.model.MatType;
import com.weframe.product.pictureframe.PictureFrame;

import javax.persistence.*;

@SuppressWarnings({"unused"})
@Entity
@Table(name = "BACK_MATS")
public class BackMat extends PictureFrameComponent{

    @ManyToOne
    @JoinColumn(name = "MAT_TYPE", nullable = false)
    private MatType matType;

    @Column(name = "OUTER_HORIZONTAL_BEZEL", nullable = false)
    private float outerHorizontalBezel;

    @Column(name = "OUTER_VERTICAL_BEZEL", nullable = false)
    private float outerVerticalBezel;

    public BackMat() {
    }

    public BackMat(final Long id,
                   final PictureFrame pictureFrame,
                   final MatType matType,
                   final float outerHorizontalBezel,
                   final float outerVerticalBezel) {
        super(id, pictureFrame);
        this.matType = matType;
        this.outerHorizontalBezel = outerHorizontalBezel;
        this.outerVerticalBezel = outerVerticalBezel;
    }

    public MatType getMatType() {
        return matType;
    }

    public void setMatType(final MatType matType) {
        this.matType = matType;
    }

    public float getOuterHorizontalBezel() {
        return outerHorizontalBezel;
    }

    public void setOuterHorizontalBezel(final float outerHorizontalBezel) {
        this.outerHorizontalBezel = outerHorizontalBezel;
    }

    public float getOuterVerticalBezel() {
        return outerVerticalBezel;
    }

    public void setOuterVerticalBezel(final float outerVerticalBezel) {
        this.outerVerticalBezel = outerVerticalBezel;
    }
}

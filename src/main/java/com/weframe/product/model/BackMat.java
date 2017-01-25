package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "BACK_MATS")
public class BackMat {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
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
                   final MatType matType,
                   final float outerHorizontalBezel,
                   final float outerVerticalBezel) {
        this.id = id;
        this.matType = matType;
        this.outerHorizontalBezel = outerHorizontalBezel;
        this.outerVerticalBezel = outerVerticalBezel;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BackMat backMat = (BackMat) o;

        if (Float.compare(backMat.outerHorizontalBezel, outerHorizontalBezel) != 0) return false;
        if (Float.compare(backMat.outerVerticalBezel, outerVerticalBezel) != 0) return false;
        if (!id.equals(backMat.id)) return false;
        return matType.equals(backMat.matType);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + matType.hashCode();
        result = 31 * result + (outerHorizontalBezel != +0.0f ? Float.floatToIntBits(outerHorizontalBezel) : 0);
        result = 31 * result + (outerVerticalBezel != +0.0f ? Float.floatToIntBits(outerVerticalBezel) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BackMat{" +
                "id=" + id +
                ", matType=" + matType +
                ", outerHorizontalBezel=" + outerHorizontalBezel +
                ", outerVerticalBezel=" + outerVerticalBezel +
                '}';
    }
}

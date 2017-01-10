package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "WINDOW_MATS")
public class WindowMat {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MAT_TYPE", nullable = false)
    private MatType type;
    @ManyToOne
    @JoinColumn(name = "MAT_COLOR", nullable = false)
    private MatColor color;
    @Column(name = "INNER_HORIZONTAL_BEZEL", nullable = false)
    private float innerHorizontalBezel;
    @Column(name = "INNER_VERTICAL_BEZEL", nullable = false)
    private float innerVerticalBezel;

    public WindowMat() {
    }

    public WindowMat(final Long id,
                     final MatType type,
                     final MatColor color,
                     final float innerHorizontalBezel,
                     final float innerVerticalBezel) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.innerHorizontalBezel = innerHorizontalBezel;
        this.innerVerticalBezel = innerVerticalBezel;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public MatType getType() {
        return type;
    }

    public void setType(final MatType type) {
        this.type = type;
    }

    public MatColor getColor() {
        return color;
    }

    public void setColor(final MatColor color) {
        this.color = color;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WindowMat windowMat = (WindowMat) o;

        if (Float.compare(windowMat.innerHorizontalBezel, innerHorizontalBezel) != 0) return false;
        if (Float.compare(windowMat.innerVerticalBezel, innerVerticalBezel) != 0) return false;
        if (!id.equals(windowMat.id)) return false;
        if (!type.equals(windowMat.type)) return false;
        return color.equals(windowMat.color);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + (innerHorizontalBezel != +0.0f ? Float.floatToIntBits(innerHorizontalBezel) : 0);
        result = 31 * result + (innerVerticalBezel != +0.0f ? Float.floatToIntBits(innerVerticalBezel) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WindowMat{" +
                "id=" + id +
                ", type=" + type +
                ", color=" + color +
                ", innerHorizontalBezel=" + innerHorizontalBezel +
                ", innerVerticalBezel=" + innerVerticalBezel +
                '}';
    }
}

package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "FRAME_GLASSES")
public class FrameGlass {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "M2_PRICE", nullable = false)
    private float m2Price;

    public FrameGlass() {
    }

    public FrameGlass(final Long id, final String name, final float m2Price) {
        this.id = id;
        this.name = name;
        this.m2Price = m2Price;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public float getM2Price() {
        return m2Price;
    }

    public void setM2Price(final float m2Price) {
        this.m2Price = m2Price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrameGlass that = (FrameGlass) o;

        if (Float.compare(that.m2Price, m2Price) != 0) return false;
        if (!id.equals(that.id)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (m2Price != +0.0f ? Float.floatToIntBits(m2Price) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FrameGlass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", m2Price=" + m2Price +
                '}';
    }
}

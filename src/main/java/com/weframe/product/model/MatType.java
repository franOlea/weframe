package com.weframe.product.model;

import javax.persistence.*;

@Entity
@Table(name = "MAT_TYPES")
public class MatType {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "IMAGE_KEY", nullable = false)
    private String imageKey;
    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;
    @Column(name = "M2_PRICE", nullable = false)
    private float m2Price;

    public MatType() {
    }

    public MatType(final Long id,
                   final String name,
                   final String imageKey,
                   final String imageUrl,
                   final float m2Price) {
        this.id = id;
        this.name = name;
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
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

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(final String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
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

        MatType matType = (MatType) o;

        if (Float.compare(matType.m2Price, m2Price) != 0) return false;
        if (!id.equals(matType.id)) return false;
        if (!name.equals(matType.name)) return false;
        if (!imageKey.equals(matType.imageKey)) return false;
        return imageUrl.equals(matType.imageUrl);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + imageKey.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + (m2Price != +0.0f ? Float.floatToIntBits(m2Price) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MatType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageKey='" + imageKey + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", m2Price=" + m2Price +
                '}';
    }
}

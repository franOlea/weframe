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

    public MatType() {
    }

    public MatType(final Long id,
                   final String name,
                   final String imageKey,
                   final String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatType matType = (MatType) o;

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
        return result;
    }

    @Override
    public String toString() {
        return "MatType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageKey='" + imageKey + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

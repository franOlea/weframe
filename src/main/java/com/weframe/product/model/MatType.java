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
    @Column(name = "IMAGE_FILE_PATH", nullable = false)
    private String imageFilePath;

    public MatType() {
    }

    public MatType(final Long id,
                   final String name,
                   final String imageFilePath) {
        this.id = id;
        this.name = name;
        this.imageFilePath = imageFilePath;
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

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatType matType = (MatType) o;

        if (!id.equals(matType.id)) return false;
        if (!name.equals(matType.name)) return false;
        return imageFilePath.equals(matType.imageFilePath);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + imageFilePath.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MatType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                '}';
    }
}

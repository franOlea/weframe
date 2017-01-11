package com.weframe.product.model;

import com.weframe.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "PICTURES")
public class Picture {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "IMAGE_FILE_PATH", nullable = false)
    private String imageFilePath;
    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    public Picture() {
    }

    public Picture(final Long id,
                   final String imageFilePath,
                   final User user) {
        this.id = id;
        this.imageFilePath = imageFilePath;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Picture picture = (Picture) o;

        if (!id.equals(picture.id)) return false;
        if (!imageFilePath.equals(picture.imageFilePath)) return false;
        return user.equals(picture.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + imageFilePath.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", user=" + user +
                '}';
    }
}

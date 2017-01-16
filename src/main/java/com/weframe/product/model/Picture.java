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
    @Column(name = "IMAGE_KEY", nullable = false)
    private String imageKey;
    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    public Picture() {
    }

    public Picture(final Long id,
                   final String imageKey,
                   final String imageUrl,
                   final User user) {
        this.id = id;
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
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

        Picture picture = (Picture) o;

        if (!id.equals(picture.id)) return false;
        if (!imageKey.equals(picture.imageKey)) return false;
        if (!imageUrl.equals(picture.imageUrl)) return false;
        return user.equals(picture.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + imageKey.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imageKey='" + imageKey + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", user=" + user +
                '}';
    }
}

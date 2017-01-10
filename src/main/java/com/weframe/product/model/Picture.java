package com.weframe.product.model;

import com.weframe.user.model.User;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "PICTURES")
public class Picture {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    public Picture() {
    }

    public Picture(final Long id,
                   final String filePath,
                   final User user) {
        this.id = id;
        this.filePath = filePath;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
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
        if (!filePath.equals(picture.filePath)) return false;
        return user.equals(picture.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + filePath.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", user=" + user +
                '}';
    }
}

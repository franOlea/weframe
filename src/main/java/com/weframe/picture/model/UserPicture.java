package com.weframe.picture.model;

import com.weframe.picture.model.Picture;
import com.weframe.user.model.User;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "USER_PICTURES")
public class UserPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    public UserPicture() {

    }

    public UserPicture(final Picture picture,
                       final User user) {
        this.picture = picture;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(final Picture picture) {
        this.picture = picture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserPicture{" +
                "id=" + id +
                ", picture=" + picture.getImageKey() +
                ", user=" + user.getEmail() +
                '}';
    }
}

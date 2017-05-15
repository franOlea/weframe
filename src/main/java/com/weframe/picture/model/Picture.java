package com.weframe.picture.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "PICTURES")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "IMAGE_KEY", nullable = false, unique = true)
    private String imageKey;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String imageUrl;

    public Picture() {
    }

    public Picture(final String imageKey) {
        this.imageKey = imageKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imageKey='" + imageKey + "\'}";
    }
}

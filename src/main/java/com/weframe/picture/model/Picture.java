package com.weframe.picture.model;

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

    @Column(name = "IMAGE_URL", nullable = false, unique = true)
    private String imageUrl;

    public Picture() {
    }

    public Picture(String imageKey, String imageUrl) {
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imageKey='" + imageKey + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

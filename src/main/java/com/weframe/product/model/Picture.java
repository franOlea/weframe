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

    @Column(name = "IMAGE_KEY", nullable = false, unique = true)
    private String imageKey;

    @Column(name = "IMAGE_URL", nullable = false, unique = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    public Picture() {
    }

}

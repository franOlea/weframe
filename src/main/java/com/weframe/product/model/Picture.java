package com.weframe.product.model;

import javax.persistence.*;

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

}

package com.weframe.product.model.persistence.generic;


import javax.persistence.*;

@Entity
@Table(name = "FRAMES")
public class Frame {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UNIQUE_NAME", nullable = false, unique = true)
    private String uniqueName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "HEIGHT", nullable = false)
    private float height;

    @Column(name = "LENGTH", nullable = false)
    private float length;

    @Column(name = "IMAGE_KEY", nullable = false, unique = true)
    private String imageKey;

    @Column(name = "IMAGE_URL", nullable = false, unique = true)
    private String imageUrl;

    @Column(name= "PRICE", nullable = false)
    private float price;

    public Frame() {
    }

}

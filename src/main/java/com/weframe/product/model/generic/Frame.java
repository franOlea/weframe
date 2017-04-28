package com.weframe.product.model.generic;


import com.weframe.picture.model.Picture;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "FRAMES")
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @Column(name= "PRICE", nullable = false)
    private float price;

    public Frame() {
    }

}

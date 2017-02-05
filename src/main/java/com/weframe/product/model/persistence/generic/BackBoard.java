package com.weframe.product.model.persistence.generic;

import javax.persistence.*;

@Entity
@Table(name = "BACK_BOARDS")
public class BackBoard {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "UNIQUE_NAME", nullable = false, unique = true)
    private String uniqueName;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "IMAGE_KEY", nullable = false, unique = true)
    private String imageKey;

    @Column(name = "IMAGE_URL", nullable = false, unique = true)
    private String imageUrl;

    @Column(name= "M2_PRICE", nullable = false)
    private float m2Price;

    public BackBoard() {
    }


}

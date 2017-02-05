package com.weframe.product.model.generic;

import javax.persistence.*;

@Entity
@Table(name = "FRAME_GLASSES")
public class FrameGlass {

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

    @Column(name = "M2_PRICE", nullable = false)
    private float m2Price;

    public FrameGlass() {
    }

}

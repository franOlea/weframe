package com.weframe.product.model.generic;

import com.weframe.product.model.Picture;

import javax.persistence.*;

@Entity
@Table(name = "MAT_TYPES")
public class MatType {

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

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @Column(name = "M2_PRICE", nullable = false)
    private float m2Price;

    public MatType() {
    }

}

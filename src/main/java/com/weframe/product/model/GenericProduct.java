package com.weframe.product.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class GenericProduct {

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

    public GenericProduct() {
    }

    public GenericProduct(final String name,
                          final String uniqueName,
                          final String description) {
        this.name = name;
        this.uniqueName = uniqueName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(final String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}

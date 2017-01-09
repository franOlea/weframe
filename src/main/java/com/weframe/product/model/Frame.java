package com.weframe.product.model;


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
    @Column(name = "HEIGHT", nullable = false)
    private float height;
    @Column(name = "LENGTH", nullable = false)
    private float length;

    public Frame() {
    }

    public Frame(final Long id,
                 final String name,
                 final float height,
                 final float length) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame frame = (Frame) o;

        if (Float.compare(frame.height, height) != 0) return false;
        if (Float.compare(frame.length, length) != 0) return false;
        if (!id.equals(frame.id)) return false;
        return name.equals(frame.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        result = 31 * result + (length != +0.0f ? Float.floatToIntBits(length) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", length=" + length +
                '}';
    }
    
}

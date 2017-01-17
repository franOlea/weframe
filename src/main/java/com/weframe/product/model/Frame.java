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
    @Column(name = "IMAGE_KEY", nullable = false)
    private String imageKey;
    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    public Frame() {
    }

    public Frame(final Long id,
                 final String name,
                 final float height,
                 final float length,
                 final String imageKey,
                 final String imageUrl) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.length = length;
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
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

    public float getHeight() {
        return height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(final float length) {
        this.length = length;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(final String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame frame = (Frame) o;

        if (Float.compare(frame.height, height) != 0) return false;
        if (Float.compare(frame.length, length) != 0) return false;
        if (!id.equals(frame.id)) return false;
        if (!name.equals(frame.name)) return false;
        if (!imageKey.equals(frame.imageKey)) return false;
        return imageUrl.equals(frame.imageUrl);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        result = 31 * result + (length != +0.0f ? Float.floatToIntBits(length) : 0);
        result = 31 * result + imageKey.hashCode();
        result = 31 * result + imageUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", length=" + length +
                ", imageKey='" + imageKey + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

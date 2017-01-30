package com.weframe.product.model.persistence.personalized;

import com.weframe.product.model.Picture;
import com.weframe.product.model.PictureFrame;

import javax.persistence.*;

@Entity
@Table(name = "LOCATED_PICTURES")
public class LocatedPicture {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "PICTURE_MASK", nullable = false)
    private PictureMask pictureMask;

    @Column(name = "LENGTH", nullable = false)
    private float length;

    @Column(name = "HEIGHT", nullable = false)
    private float height;

    @Column(name = "X_POSITION", nullable = false)
    private float xPosition;

    @Column(name = "Y_POSITION", nullable = false)
    private float yPosition;

    @Column(name = "Z_INDEX", nullable = false)
    private int zIndex;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "PICTURE_FRAME", nullable = false)
    private PictureFrame pictureFrame;

    public LocatedPicture() {
    }

}

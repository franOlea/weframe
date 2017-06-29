package com.weframe.product.personalized.model;

import com.weframe.product.generic.model.MatType;
import com.weframe.product.pictureframe.PictureFrame;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "BACK_MATS")
public class BackMat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MAT_TYPE", nullable = false)
    private MatType matType;

    @Column(name = "OUTER_HORIZONTAL_BEZEL", nullable = false)
    private float outerHorizontalBezel;

    @Column(name = "OUTER_VERTICAL_BEZEL", nullable = false)
    private float outerVerticalBezel;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "PICTURE_FRAME", nullable = false)
    private PictureFrame pictureFrame;

    public BackMat() {
    }

}

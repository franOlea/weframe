package com.weframe.product.personalized.model;

import com.weframe.product.pictureframe.model.PictureFrame;
import com.weframe.product.generic.model.MatType;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "WINDOW_MATS")
public class WindowMat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MAT_TYPE", nullable = false)
    private MatType matType;

    @Column(name = "INNER_HORIZONTAL_BEZEL", nullable = false)
    private float innerHorizontalBezel;

    @Column(name = "INNER_VERTICAL_BEZEL", nullable = false)
    private float innerVerticalBezel;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "PICTURE_FRAME", nullable = false)
    private PictureFrame pictureFrame;

    public WindowMat() {
    }

}

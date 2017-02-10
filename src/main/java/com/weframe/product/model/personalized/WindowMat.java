package com.weframe.product.model.personalized;

import com.weframe.product.model.PictureFrame;
import com.weframe.product.model.generic.MatType;

import javax.persistence.*;

@Entity
@Table(name = "WINDOW_MATS")
public class WindowMat {

    @Id
    @GeneratedValue
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

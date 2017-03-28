package com.weframe.product.model.personalized;

import javax.persistence.*;

@Entity
@Table(name = "PICTURE_MASKS")
public class PictureMask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "X_OFFSET", nullable = false)
    private float xOffset;

    @Column(name = "LENGTH", nullable = false)
    private float lenght;

    @Column(name = "Y_OFFSET", nullable = false)
    private float yOffset;

    @Column(name = "HEIGHT", nullable = false)
    private float height;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "LOCATED_PICTURE", nullable = false)
    private LocatedPicture pictureFrame;

    public PictureMask() {
    }

}

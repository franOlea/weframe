package com.weframe.product.pictureframe;

import com.weframe.product.generic.model.BackBoard;
import com.weframe.product.generic.model.Frame;
import com.weframe.product.generic.model.FrameGlass;
import com.weframe.product.personalized.model.BackMat;
import com.weframe.product.personalized.model.LocatedPicture;
import com.weframe.product.personalized.model.WindowMat;
import com.weframe.user.model.User;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "PICTURE_FRAMES")
public class PictureFrame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BACK_BOARD", nullable = false)
    private BackBoard backBoard;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "BACK_MAT", nullable = false)
    private BackMat backMat;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            mappedBy = "pictureFrame")
    private Set<LocatedPicture> locatedPictures;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "WINDOW_MAT", nullable = false)
    private WindowMat windowMat;

    @ManyToOne
    @JoinColumn(name = "FRAME_GLASS", nullable = false)
    private FrameGlass frameGlass;

    @ManyToOne
    @JoinColumn(name = "FRAME", nullable = false)
    private Frame frame;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

    public PictureFrame() {
    }

}

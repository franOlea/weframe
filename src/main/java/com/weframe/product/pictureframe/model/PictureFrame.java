package com.weframe.product.pictureframe.model;

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

    public PictureFrame(final Long id,
                        final BackBoard backBoard,
                        final BackMat backMat,
                        final Set<LocatedPicture> locatedPictures,
                        final WindowMat windowMat,
                        final FrameGlass frameGlass,
                        final Frame frame,
                        final User user) {
        this.id = id;
        this.backBoard = backBoard;
        this.backMat = backMat;
        this.locatedPictures = locatedPictures;
        this.windowMat = windowMat;
        this.frameGlass = frameGlass;
        this.frame = frame;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BackBoard getBackBoard() {
        return backBoard;
    }

    public void setBackBoard(final BackBoard backBoard) {
        this.backBoard = backBoard;
    }

    public BackMat getBackMat() {
        return backMat;
    }

    public void setBackMat(final BackMat backMat) {
        this.backMat = backMat;
    }

    public Set<LocatedPicture> getLocatedPictures() {
        return locatedPictures;
    }

    public void setLocatedPictures(final Set<LocatedPicture> locatedPictures) {
        this.locatedPictures = locatedPictures;
    }

    public WindowMat getWindowMat() {
        return windowMat;
    }

    public void setWindowMat(final WindowMat windowMat) {
        this.windowMat = windowMat;
    }

    public FrameGlass getFrameGlass() {
        return frameGlass;
    }

    public void setFrameGlass(final FrameGlass frameGlass) {
        this.frameGlass = frameGlass;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(final Frame frame) {
        this.frame = frame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}

package com.weframe.product.model;

import com.weframe.user.model.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PICTURE_FRAMES")
public class PictureFrame {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "BACK_BOARD", nullable = false)
    private BackBoard backBoard;
    @ManyToOne
    @JoinColumn(name = "BACK_MAT", nullable = false)
    private BackMat backMat;
    @OneToMany
    private Set<LocatedPicture> locatedPictures;
    @ManyToOne
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PictureFrame that = (PictureFrame) o;

        if (!id.equals(that.id)) return false;
        if (!backBoard.equals(that.backBoard)) return false;
        if (!backMat.equals(that.backMat)) return false;
        if (!locatedPictures.equals(that.locatedPictures)) return false;
        if (!windowMat.equals(that.windowMat)) return false;
        if (!frameGlass.equals(that.frameGlass)) return false;
        if (!frame.equals(that.frame)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + backBoard.hashCode();
        result = 31 * result + backMat.hashCode();
        result = 31 * result + locatedPictures.hashCode();
        result = 31 * result + windowMat.hashCode();
        result = 31 * result + frameGlass.hashCode();
        result = 31 * result + frame.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PictureFrame{" +
                "id=" + id +
                ", backBoard=" + backBoard +
                ", backMat=" + backMat +
                ", locatedPictures=" + locatedPictures +
                ", windowMat=" + windowMat +
                ", frameGlass=" + frameGlass +
                ", frame=" + frame +
                ", user=" + user +
                '}';
    }
}

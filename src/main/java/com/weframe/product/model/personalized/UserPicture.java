package com.weframe.product.model.personalized;

import com.weframe.product.model.Picture;
import com.weframe.user.model.User;

import javax.persistence.*;

public class UserPicture {

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "PICTURE", nullable = false)
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private User user;

}

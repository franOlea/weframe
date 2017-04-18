package com.weframe.product.model.personalized;

import com.weframe.product.model.Picture;
import com.weframe.user.model.User;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "USER_PICTURES")
public class UserPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

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

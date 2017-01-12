package com.weframe.product.service;

import com.weframe.product.model.Picture;
import com.weframe.user.model.User;

import java.util.Set;

public interface PictureService {

    Picture getById(Long id);
    Set<Picture> getByUser(User user);
    void insert(Picture picture);
    void remove(Long id);

}

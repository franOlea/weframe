package com.weframe.product.service;

import com.weframe.product.model.Picture;
import com.weframe.user.model.User;

import java.util.List;

public interface PictureService {

    Picture getById(Long id);
    List<Picture> getByUser(User user);
    void insert(Picture picture);
    void remove(Long id);

}

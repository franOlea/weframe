package com.weframe.product.service.impl;

import com.weframe.product.model.Picture;
import com.weframe.product.service.PictureService;
import com.weframe.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PictureRepository extends JpaRepository<Picture, Long>, PictureService {

    default Picture getById(Long id) {
        return findOne(id);
    }

    default Set<Picture> getByUser(User user) {
        return findByUser(user);
    }

    default void insert(Picture picture) {
        save(picture);
    }

    default void remove(Long id) {
        delete(id);
    }

    Set<Picture> findByUser(User user);
}

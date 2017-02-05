package com.weframe.product.service;

import com.weframe.picture.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "pictures", path = "pictures")
public interface PictureRepository extends JpaRepository<Picture, Long> {

    @RestResource(path = "by-user", rel = "by-user")
    Set<Picture> findByUserEmail(String email);

}

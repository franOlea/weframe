package com.weframe.product.service;

import com.weframe.product.model.PictureFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "picture-frame", path = "picture-frame")
public interface PictureFrameRepository extends JpaRepository<PictureFrame, Long> {

    @RestResource(path = "by-user", rel = "by-user")
    Set<PictureFrame> findByUserEmail(String email);

}

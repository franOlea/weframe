package com.weframe.product.service;

import com.weframe.product.model.Frame;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "frame", path = "frame")
public interface FrameRepository {

    @RestResource(path = "like-name", rel = "like-name")
    Set<Frame> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);

    @RestResource(path = "by-image-key", rel = "by-image-key")
    Frame findByImageKey(String imageKey);

}

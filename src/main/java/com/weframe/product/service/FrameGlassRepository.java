package com.weframe.product.service;

import com.weframe.product.model.FrameGlass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "frame-glass", path = "frame-glass")
public interface FrameGlassRepository extends JpaRepository<FrameGlass, Long> {

    @RestResource(path = "like-name", rel = "like-name")
    Set<FrameGlass> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);

}

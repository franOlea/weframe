package com.weframe.product.service.repositories;

import com.weframe.product.model.persistence.generic.Frame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "frame", path = "frame")
public interface FrameRepository extends JpaRepository<Frame, Long> {

    @RestResource(path = "like-name", rel = "like-name")
    Set<Frame> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);

}

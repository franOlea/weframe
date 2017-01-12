package com.weframe.product.service.impl;

import com.weframe.product.model.BackBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "backboards", path = "backboards")
public interface BackBoardRepository extends JpaRepository<BackBoard, Long> {

    @RestResource(path = "likeName", rel = "likeName")
    Set<BackBoard> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);

}

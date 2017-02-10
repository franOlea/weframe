package com.weframe.product.service;

import com.weframe.product.model.generic.BackBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "backboards", path = "backboards")
public interface BackBoardRepository extends JpaRepository<BackBoard, Long> {

    @RestResource(path = "like-name", rel = "like-name")
    Set<BackBoard> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);

    @RestResource(path = "like-description", rel = "like-description")
    Set<BackBoard> findLikeDescriptionIgnoreCaseByOrderByNameAsc(@Param("description") String description);

    @RestResource(path = "unique-name", rel = "unique-name")
    BackBoard findByUniqueName(@Param("unique-name") String uniqueName);

}

package com.weframe.product.service.repositories;


import com.weframe.product.model.persistence.generic.MatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "mat-type", path = "mat-type")
public interface MatTypeRepository extends JpaRepository<MatType, Long> {

    @RestResource(path = "like-name", rel = "like-name")
    Set<MatType> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);

    @RestResource(path = "by-image-key", rel = "by-image-key")
    MatType findByImageKey(String imageKey);

}

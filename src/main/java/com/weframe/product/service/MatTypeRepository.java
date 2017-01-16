package com.weframe.product.service;


import com.weframe.product.model.MatType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "mat-type", path = "mat-type")
public interface MatTypeRepository {

    @RestResource(path = "like-name", rel = "like-name")
    Set<MatType> findLikeNameIgnoreCaseByOrderByNameAsc(@Param("name") String name);


    @RestResource(path = "by-image-key", rel = "by-image-key")
    MatType findByImageKey(String imageKey);

}

package com.weframe.product.service;

import com.weframe.product.model.BackMat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "back-mat", path = "back-mat")
public interface BackMatRepository extends JpaRepository<BackMat, Long> {

    @RestResource(path = "like-name", rel = "like-name")
    Set<BackMat> findByMatTypeName(@Param("matTypeName") String matTypeName);

}

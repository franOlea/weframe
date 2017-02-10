package com.weframe.product.service;

import com.weframe.product.model.personalized.BackMat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "back-mat", path = "back-mat")
public interface BackMatRepository extends JpaRepository<BackMat, Long> {

}

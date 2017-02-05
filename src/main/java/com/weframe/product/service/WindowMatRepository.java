package com.weframe.product.service;

import com.weframe.product.model.persistence.personalized.WindowMat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "window-mat", path = "window-mat")
public interface WindowMatRepository extends JpaRepository<WindowMat, Long> {



}

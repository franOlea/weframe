package com.weframe.product.service;

import com.weframe.product.model.PictureMask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "picture-mask", path = "picture-mask")
public interface PictureMaskRepository extends JpaRepository<PictureMask, Long> {



}

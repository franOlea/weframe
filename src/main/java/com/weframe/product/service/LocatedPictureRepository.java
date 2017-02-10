package com.weframe.product.service;

import com.weframe.product.model.personalized.LocatedPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "located-picture", path = "located-picture")
public interface LocatedPictureRepository extends JpaRepository<LocatedPicture, Long> {



}

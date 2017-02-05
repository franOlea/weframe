package com.weframe.product.service.repositories;

import com.weframe.product.model.persistence.personalized.LocatedPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "located-picture", path = "located-picture")
public interface LocatedPictureRepository extends JpaRepository<LocatedPicture, Long> {



}

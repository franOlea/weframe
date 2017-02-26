package com.weframe.user.newservice;

import com.weframe.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.PathVariable;

@RepositoryRestResource(collectionResourceRel = "new-users", path = "new-users")
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "/by-email/{email:.+}", rel = "/by-email/{email:.+}")
    User findByEmail(@PathVariable String email);

}

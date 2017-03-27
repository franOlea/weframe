package com.weframe.user.nservice;

import com.weframe.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
//@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "/by-email/{email:.+}", rel = "/by-email/{email:.+}")
    User findByEmail(@PathVariable String email);

    @RestResource(path = "/by-email-and-password/", rel = "/by-email-and-password/", exported = false)
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}

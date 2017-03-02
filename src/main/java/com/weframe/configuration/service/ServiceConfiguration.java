package com.weframe.configuration.service;

import com.weframe.user.model.Role;
import com.weframe.user.model.State;
import com.weframe.user.nservice.BeforeCreateUserValidator;
import com.weframe.user.nservice.UserPasswordCryptographer;
import com.weframe.user.resource.UserResourceAssembler;
import com.weframe.user.service.impl.RoleRepository;
import com.weframe.user.service.impl.StateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Value("${service.user.password.hash.iterations}")
    private int userPasswordCryptographerHashIterations;

    @Bean
    public UserPasswordCryptographer getUserPasswordCryptographer() {
        return new UserPasswordCryptographer(userPasswordCryptographerHashIterations);
    }

    @Bean
    public UserResourceAssembler getUserResourceAssembler() {
        return new UserResourceAssembler();
    }

//    @Bean(name = "beforeCreateUserValidator")
//    public BeforeCreateUserValidator getBeforeCreateUserValidator() {
//        return new BeforeCreateUserValidator();
//    }

    @Bean
    public Role getUserInitialRole(RoleRepository roleRepository) {
        return roleRepository.getDefaultRole();
    }

    @Bean
    public State getUserInitialState(StateRepository stateRepository) {
        return stateRepository.getDefaultState();
    }
}

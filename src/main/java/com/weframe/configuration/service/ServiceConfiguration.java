package com.weframe.configuration.service;

import com.weframe.user.resource.UserResourceAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public UserResourceAssembler getUserResourceAssembler() {
        return new UserResourceAssembler();
    }

}

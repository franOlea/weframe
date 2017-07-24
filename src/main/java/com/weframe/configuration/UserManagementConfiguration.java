package com.weframe.configuration;

import com.weframe.user.service.security.UserPasswordCryptographer;
import com.weframe.user.service.security.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;

@SuppressWarnings("unused")
@Configuration
public class UserManagementConfiguration {

    @Value("${service.user.password.hash.iterations}")
    private int userPasswordCryptographerHashIterations;

    @Bean
    public UserPasswordCryptographer getUserPasswordCryptographer() throws GeneralSecurityException {
        return new UserPasswordCryptographer(userPasswordCryptographerHashIterations);
    }

    @Bean
    public UserValidator getUserValidator() {
        return new UserValidator();
    }
}

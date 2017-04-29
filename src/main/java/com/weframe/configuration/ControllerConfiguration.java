package com.weframe.configuration;

import com.weframe.controllers.ResponseGenerator;
import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.BackBoardRepository;
import com.weframe.product.service.BackBoardService;
import com.weframe.product.service.impl.BackBoardServiceImpl;
import com.weframe.user.service.UserPasswordCryptographer;
import com.weframe.user.service.UserValidator;
import com.weframe.user.service.persistence.RoleRepository;
import com.weframe.user.service.persistence.StateRepository;
import com.weframe.user.service.persistence.UserRepository;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;

@SuppressWarnings("unused")
@Configuration
public class ControllerConfiguration {
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

    @Bean
    public UserService getUserService(final UserRepository userRepository,
                                      final RoleRepository roleRepository,
                                      final StateRepository stateRepository,
                                      final UserPasswordCryptographer passwordCryptographer,
                                      final UserValidator userValidator) {
        return new UserServiceImpl(userRepository, roleRepository, stateRepository, passwordCryptographer, userValidator);
    }

    @Bean
    public BackBoardService getBackBoardService(final BackBoardRepository backBoardRepository) {
        return new BackBoardServiceImpl(backBoardRepository);
    }

    @Bean
    public ResponseGenerator<BackBoard> getBackBoardResponseGenerator() {
        return new ResponseGenerator<>();
    }
}

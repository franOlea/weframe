package com.weframe.configuration;

import com.weframe.product.service.impl.BackBoardJpaRepository;
import com.weframe.product.service.impl.BackBoardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class GenericProductControllersConfiguration {

    @Bean
    public BackBoardService getBackBoardService(
            final BackBoardJpaRepository backBoardJpaRepository) {
        return new BackBoardService(backBoardJpaRepository);
    }

}

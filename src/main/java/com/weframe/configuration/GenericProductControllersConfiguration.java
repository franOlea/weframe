package com.weframe.configuration;

import com.weframe.product.generic.service.impl.BackBoardJpaRepository;
import com.weframe.product.generic.service.impl.BackBoardService;
import org.springframework.context.annotation.Bean;

//@Configuration
public class GenericProductControllersConfiguration {

    @Bean
    public BackBoardService getBackBoardService(
            final BackBoardJpaRepository backBoardJpaRepository) {
        return new BackBoardService(backBoardJpaRepository);
    }

}

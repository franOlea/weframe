package com.weframe.configuration;


import com.weframe.picture.service.*;
import com.weframe.picture.service.impl.PictureServiceImpl;
import com.weframe.picture.service.impl.UserPictureServiceImpl;
import com.weframe.product.generic.model.BackBoard;
import com.weframe.product.generic.model.Frame;
import com.weframe.product.generic.model.FrameGlass;
import com.weframe.product.generic.model.MatType;
import com.weframe.product.generic.service.GenericProductRepository;
import com.weframe.product.generic.service.impl.BackBoardService;
import com.weframe.product.generic.service.impl.FrameGlassService;
import com.weframe.product.generic.service.impl.FrameService;
import com.weframe.product.generic.service.impl.MatTypeService;
import com.weframe.user.service.persistence.RoleRepository;
import com.weframe.user.service.persistence.StateRepository;
import com.weframe.user.service.persistence.UserRepository;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.persistence.impl.UserServiceImpl;
import com.weframe.user.service.security.UserPasswordCryptographer;
import com.weframe.user.service.security.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class ServiceConfiguration {

    @Value("${picture.file.thumbnail.suffix}")
    private String thumbnailSuffix;
    @Value("${picture.file.thumbnail.width}")
    private int thumbnailWidth;
    @Value("${picture.file.thumbnail.height}")
    private int thumbnailHeight;

    @Bean
    public UserService getUserService(final UserRepository userRepository,
                                      final RoleRepository roleRepository,
                                      final StateRepository stateRepository,
                                      final UserPasswordCryptographer passwordCryptographer,
                                      final UserValidator userValidator) {
        return new UserServiceImpl(
                userRepository,
                roleRepository,
                stateRepository,
                passwordCryptographer,
                userValidator
        );
    }

    @Bean
    public PictureService getPictureService(final PictureRepository pictureRepository,
                                            final PictureFileRepository pictureFileRepository) {
        return new PictureServiceImpl(
                pictureRepository,
                pictureFileRepository,
                thumbnailSuffix,
                thumbnailWidth,
                thumbnailHeight
        );
    }

    @Bean
    public FrameService getFrameService(
            final GenericProductRepository<Frame> frameGenericProductRepository) {
        return new FrameService(frameGenericProductRepository);
    }

    @Bean
    public BackBoardService getBackBoardService(
            final GenericProductRepository<BackBoard> backBoardGenericProductRepository) {
        return new BackBoardService(backBoardGenericProductRepository);
    }

    @Bean
    public MatTypeService getMatTypeService(
            final GenericProductRepository<MatType> matTypeGenericProductRepository) {
        return new MatTypeService(matTypeGenericProductRepository);
    }

    @Bean
    public FrameGlassService getFrameGlassService(
            final GenericProductRepository<FrameGlass> frameGlassGenericProductRepository) {
        return new FrameGlassService(frameGlassGenericProductRepository);
    }

    @Bean
    public UserPictureService getUserPictureService(final UserService userService,
                                                    final PictureService pictureService,
                                                    final UserPictureRepository userPictureRepository) {
        return new UserPictureServiceImpl(pictureService, userService, userPictureRepository);
    }
}

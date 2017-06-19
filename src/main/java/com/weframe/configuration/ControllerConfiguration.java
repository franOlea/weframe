package com.weframe.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.impl.PictureFileInMemoryRepository;
import com.weframe.picture.service.impl.PictureFileS3Repository;
import com.weframe.picture.service.impl.PictureServiceImpl;
import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.model.generic.Frame;
import com.weframe.product.model.generic.FrameGlass;
import com.weframe.product.model.generic.MatType;
import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.impl.BackBoardService;
import com.weframe.product.service.impl.FrameGlassService;
import com.weframe.product.service.impl.FrameService;
import com.weframe.product.service.impl.MatTypeService;
import com.weframe.user.model.User;
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
import org.springframework.context.annotation.Profile;

import java.security.GeneralSecurityException;
import java.util.HashMap;

@SuppressWarnings("unused")
@Configuration
public class ControllerConfiguration {

    @Value("${service.user.password.hash.iterations}")
    private int userPasswordCryptographerHashIterations;
    @Value("${temp.directory}")
    private String pictureTempFileDirectory;
    @Value("${picture.file.aws.bucket.name}")
    private String s3BucketName;
    @Value("${picture.file.aws.access.key}")
    private String awsAccessKey;
    @Value("${picture.file.aws.secret.key}")
    private String awsSecretKey;
    @Value("${temp.directory}")
    private String tempDirectory;
    @Value("${picture.file.image.format.name}")
    private String imageFormatName;
    @Value("${picture.file.thumbnail.suffix}")
    private String thumbnailSuffix;
    @Value("${picture.file.thumbnail.width}")
    private int thumbnailWidth;
    @Value("${picture.file.thumbnail.height}")
    private int thumbnailHeight;

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
        return new UserServiceImpl(
                userRepository,
                roleRepository,
                stateRepository,
                passwordCryptographer,
                userValidator
        );
    }

    @Bean
    @Profile("embedded")
    public PictureFileRepository getPictureFileInMemoryRepository() {
        return new PictureFileInMemoryRepository(new HashMap<>());
    }

    @Bean
    @Profile("openshift")
    public PictureFileRepository getPictureFileS3Repository() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3Client.builder()
                .withClientConfiguration(clientConfiguration)
                .withCredentials(credentialsProvider)
                .withRegion(Regions.SA_EAST_1)
                .build();

        return new PictureFileS3Repository(
                pictureTempFileDirectory,
                amazonS3Client,
                s3BucketName
        );
    }

    @Bean(name = "tempDirectory")
    public String getTempDirectory() {
        return tempDirectory;
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
    public ResponseGenerator<User> getUserResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<BackBoard> getBackBoardResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<Picture> getPictureResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public ResponseGenerator<Frame> getFrameResponseGenerator() {
        return new ResponseGenerator<>();
    }

    @Bean
    public BackBoardService getBackBoardService(
            final GenericProductRepository<BackBoard> backBoardGenericProductRepository) {
        return new BackBoardService(backBoardGenericProductRepository);
    }

    @Bean
    public ResponseGenerator<FrameGlass> getFrameGlassResponseGenerator() {
        return new ResponseGenerator<>();
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
    public ResponseGenerator<MatType> getMatTypeResponseGenerator() {
        return new ResponseGenerator<>();
    }
}

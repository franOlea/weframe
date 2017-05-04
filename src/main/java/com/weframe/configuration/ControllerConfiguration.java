package com.weframe.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Region;
import com.weframe.controllers.ResponseGenerator;
import com.weframe.picture.model.Picture;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.PictureRepository;
import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.impl.PictureFileInMemoryRepository;
import com.weframe.picture.service.impl.PictureFileS3Repository;
import com.weframe.picture.service.impl.PictureServiceImpl;
import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.GenericProductRepository;
import com.weframe.product.service.impl.BackBoardService;
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
    @Value("${picture.file.s3.bucket.name}")
    private String s3BucketName;
    @Value("${picture.file.aws.access.key}")
    private String awsAccessKey;
    @Value("${picture.file.aws.secret.key}")
    private String awsSecretKey;

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
    @Profile("local")
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
                .withRegion(Region.SA_SaoPaulo.name())
                .build();

        return new PictureFileS3Repository(
                pictureTempFileDirectory,
                amazonS3Client,
                s3BucketName
        );
    }

    @Bean
    public PictureService getPictureService(final PictureRepository pictureRepository,
                                            final PictureFileRepository pictureFileRepository) {
        return new PictureServiceImpl(
                pictureRepository,
                pictureFileRepository
        );
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
    public BackBoardService getBackBoardService(
            final GenericProductRepository<BackBoard> backBoardGenericProductRepository) {
        return new BackBoardService(backBoardGenericProductRepository);
    }
}

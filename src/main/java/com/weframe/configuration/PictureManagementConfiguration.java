package com.weframe.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.impl.PictureFileInMemoryRepository;
import com.weframe.picture.service.impl.PictureFileS3Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;

@SuppressWarnings("unused")
@Configuration
public class PictureManagementConfiguration {

    @Value("${temp.directory}")
    private String tempDirectory;
    @Value("${temp.directory}")
    private String pictureTempFileDirectory;
    @Value("${picture.file.aws.bucket.name}")
    private String s3BucketName;
    @Value("${picture.file.aws.access.key}")
    private String awsAccessKey;
    @Value("${picture.file.aws.secret.key}")
    private String awsSecretKey;
    @Value("server.port")
    private String serverPort;
    @Value("server.address")
    private String serverAddress;

    @Bean
    @Profile("embedded")
    public PictureFileInMemoryRepository getPictureFileInMemoryRepository() {
        return new PictureFileInMemoryRepository(new HashMap<>(), serverAddress, serverPort);
    }

    @Bean
    @Profile({"openshift", "aws"})
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

}

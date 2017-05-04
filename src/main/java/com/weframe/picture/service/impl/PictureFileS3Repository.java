package com.weframe.picture.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class PictureFileS3Repository implements PictureFileRepository {

    private final String tempDirectory;
    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    public PictureFileS3Repository(final String tempDirectory,
                                   final AmazonS3Client amazonS3Client,
                                   final String bucketName) {
        this.tempDirectory = tempDirectory;
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    @Override
    public File getFileByKey(String uniqueKey) throws PictureFileIOException {
        try {
            InputStream objectInputStream = amazonS3Client.getObject(bucketName, uniqueKey).getObjectContent();
            File pictureFile = new File(tempDirectory + File.pathSeparatorChar + UUID.randomUUID());
            FileUtils.copyInputStreamToFile(objectInputStream, pictureFile);
            return pictureFile;
        } catch (IOException | SdkClientException e) {
            throw new PictureFileIOException(e);
        }
    }

    @Override
    public String getFileUrl(String uniqueKey) throws PictureFileIOException {
        try {
            Date expiration = new Date();
            long expirationTime = expiration.getTime();
            expirationTime += 1000 * 60 * 60; // 1 hour.
            expiration.setTime(expirationTime);
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, uniqueKey);

            generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
            generatePresignedUrlRequest.setExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch(SdkClientException e) {
            throw new PictureFileIOException(e);
        }
    }

    @Override
    public void putFile(File file, String uniqueKey) throws PictureFileIOException {
        try {
            amazonS3Client.putObject(bucketName, uniqueKey, file);
        } catch (SdkClientException e) {
            throw new PictureFileIOException(e);
        }
    }

    @Override
    public void deleteFile(String uniqueKey) throws PictureFileIOException {
        try {
            amazonS3Client.deleteObject(bucketName, uniqueKey);
        } catch (SdkClientException e) {
            throw new PictureFileIOException(e);
        }
    }

}

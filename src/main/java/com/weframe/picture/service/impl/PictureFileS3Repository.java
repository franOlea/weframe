package com.weframe.picture.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private final String imageFormatName;

    public PictureFileS3Repository(final String tempDirectory,
                                   final AmazonS3Client amazonS3Client,
                                   final String bucketName,
                                   final String imageFormatName) {
        this.tempDirectory = tempDirectory;
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
        this.imageFormatName = imageFormatName;
    }

    @Override
    public BufferedImage getPictureByKey(String uniqueKey) throws PictureFileIOException {
        try {
            InputStream objectInputStream = amazonS3Client.getObject(bucketName, uniqueKey).getObjectContent();
            return ImageIO.read(objectInputStream);
        } catch (IOException | SdkClientException e) {
            throw new PictureFileIOException(e);
        }
    }

    @Override
    public String getPictureUrl(String uniqueKey) throws PictureFileIOException {
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
    public void putPicture(BufferedImage bufferedImage, String uniqueKey) throws PictureFileIOException {
        File imageFile = new File(tempDirectory + File.pathSeparatorChar + UUID.randomUUID());
        try {
            ImageIO.write(bufferedImage, imageFormatName, imageFile);
            amazonS3Client.putObject(
                    bucketName,
                    uniqueKey,
                    imageFile
            );
        } catch (SdkClientException | IOException e) {
            throw new PictureFileIOException(e);
        } finally {
            imageFile.delete();
        }
    }

    @Override
    public void deletePicture(String uniqueKey) throws PictureFileIOException {
        try {
            amazonS3Client.deleteObject(bucketName, uniqueKey);
        } catch (SdkClientException e) {
            throw new PictureFileIOException(e);
        }
    }

}

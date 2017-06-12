package com.weframe.picture.service;

import com.weframe.picture.service.exception.PictureFileIOException;

import java.awt.image.BufferedImage;

public interface PictureFileRepository {

    BufferedImage getPictureByKey(String uniqueKey) throws PictureFileIOException;

    String getPictureUrl(String uniqueKey) throws PictureFileIOException;

    void putPicture(BufferedImage bufferedImage, String uniqueKey, String formatName) throws PictureFileIOException;

    void deletePicture(String uniqueKey) throws PictureFileIOException;

}

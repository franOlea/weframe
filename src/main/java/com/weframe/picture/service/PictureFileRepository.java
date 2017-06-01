package com.weframe.picture.service;

import com.weframe.picture.service.exception.PictureFileIOException;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface PictureFileRepository {

    BufferedImage getPictureByKey(String uniqueKey) throws PictureFileIOException;

    String getPictureUrl(String uniqueKey) throws PictureFileIOException;

    void putPicture(BufferedImage bufferedImage, String uniqueKey, String formatName) throws PictureFileIOException;

    void deletePicture(String uniqueKey) throws PictureFileIOException;

}

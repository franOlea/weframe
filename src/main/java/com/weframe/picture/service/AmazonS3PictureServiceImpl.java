package com.weframe.picture.service;

import com.weframe.picture.service.exceptions.PictureGetException;
import com.weframe.picture.service.exceptions.PicturePutException;

import java.io.File;

public class AmazonS3PictureServiceImpl implements PictureService {

    @Override
    public void putPictureFile(String storageUnit, String fileKey, File pictureFile) throws PicturePutException {

    }

    @Override
    public File getPictureFile(String storageUnit, String fileKey) throws PictureGetException {
        return null;
    }

    @Override
    public String getPictureUrl(String storageUnit, String fileKey) throws PictureGetException {
        return null;
    }
}

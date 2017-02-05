package com.weframe.picture.service;


import com.weframe.picture.service.exceptions.PictureGetException;
import com.weframe.picture.service.exceptions.PicturePutException;

import java.io.File;

public interface PictureService {

    void putPictureFile(String storageUnit, String fileKey, File pictureFile) throws PicturePutException;
    File getPictureFile(String storageUnit, String fileKey) throws PictureGetException;
    String getPictureUrl(String storageUnit, String fileKey) throws PictureGetException;

}

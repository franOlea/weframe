package com.weframe.picture.service;

import com.weframe.picture.service.exception.PictureFileIOException;

import java.io.File;

public interface PictureFileRepository {

    File getFileByKey(String uniqueKey) throws PictureFileIOException;

    String getFileUrl(String uniqueKey) throws PictureFileIOException;

    void putFile(File file, String uniqueKey) throws PictureFileIOException;

    void deleteFile(String uniqueKey) throws PictureFileIOException;

}

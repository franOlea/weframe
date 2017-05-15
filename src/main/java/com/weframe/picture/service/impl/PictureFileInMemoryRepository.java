package com.weframe.picture.service.impl;

import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;

import java.io.File;
import java.util.Map;

public class PictureFileInMemoryRepository implements PictureFileRepository {

    private final Map<String, File> picturesMap;

    public PictureFileInMemoryRepository(final Map<String, File> picturesMap) {
        this.picturesMap = picturesMap;
    }

    @Override
    public File getFileByKey(String uniqueKey) throws PictureFileIOException {
        return picturesMap.get(uniqueKey);
    }

    @Override
    public String getFileUrl(String uniqueKey) throws PictureFileIOException {
        return picturesMap.get(uniqueKey).toURI().getPath().substring(1);
    }

    @Override
    public void putFile(File file, String uniqueKey) throws PictureFileIOException {
        picturesMap.put(uniqueKey, file);
    }

    @Override
    public void deleteFile(String uniqueKey) throws PictureFileIOException {
        picturesMap.remove(uniqueKey);
    }
}

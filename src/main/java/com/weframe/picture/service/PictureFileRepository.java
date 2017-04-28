package com.weframe.picture.service;

import java.io.File;

public interface PictureFileRepository {

    File getFileByKey(String uniqueKey);

    String putFile(File file, String uniqueKey);

    void deleteFile(String uniqueKey);

}

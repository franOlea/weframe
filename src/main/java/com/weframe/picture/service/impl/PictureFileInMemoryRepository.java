package com.weframe.picture.service.impl;

import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class PictureFileInMemoryRepository implements PictureFileRepository {

    private final Map<String, BufferedImage> picturesMap;

    public PictureFileInMemoryRepository(final Map<String, BufferedImage> picturesMap) {
        this.picturesMap = picturesMap;
    }

    @Override
    public BufferedImage getPictureByKey(String uniqueKey) throws PictureFileIOException {
        return picturesMap.get(uniqueKey);
    }

    @Override
    public String getPictureUrl(String uniqueKey) throws PictureFileIOException {
        BufferedImage image = picturesMap.get(uniqueKey);
        File imageFile = new File("target/" + UUID.randomUUID().toString());
        try {
            ImageIO.write(image, "jpg", imageFile);
        } catch (IOException e) {
            throw new PictureFileIOException(e);
        }
        return imageFile.toURI().getPath().substring(1);
    }

    @Override
    public void putPicture(BufferedImage bufferedImage, String uniqueKey) throws PictureFileIOException {
        picturesMap.put(uniqueKey, bufferedImage);
    }

    @Override
    public void deletePicture(String uniqueKey) throws PictureFileIOException {
        picturesMap.remove(uniqueKey);
    }
}

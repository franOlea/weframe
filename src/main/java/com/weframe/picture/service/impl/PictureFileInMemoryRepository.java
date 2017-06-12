package com.weframe.picture.service.impl;

import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;
import org.springframework.data.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class PictureFileInMemoryRepository implements PictureFileRepository {

    private final Map<String, Pair<BufferedImage, String>> picturesMap;

    public PictureFileInMemoryRepository(final Map<String, Pair<BufferedImage, String>> picturesMap) {
        this.picturesMap = picturesMap;
    }

    @Override
    public BufferedImage getPictureByKey(final String uniqueKey) throws PictureFileIOException {
        return picturesMap.get(uniqueKey).getFirst();
    }

    @Override
    public String getPictureUrl(final String uniqueKey) throws PictureFileIOException {
        Pair<BufferedImage, String> image = picturesMap.get(uniqueKey);
        File imageFile = new File("target/" + UUID.randomUUID().toString());
        try {
            ImageIO.write(image.getFirst(), image.getSecond(), imageFile);
        } catch (IOException e) {
            throw new PictureFileIOException(e);
        }
        return imageFile.toURI().getPath().substring(1);
    }

    @Override
    public void putPicture(final BufferedImage bufferedImage,
                           final String uniqueKey,
                           final String formatName) throws PictureFileIOException {
        picturesMap.put(uniqueKey, Pair.of(bufferedImage, formatName));
    }

    @Override
    public void deletePicture(final String uniqueKey) throws PictureFileIOException {
        picturesMap.remove(uniqueKey);
    }
}

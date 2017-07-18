package com.weframe.picture.service.impl;

import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;

import java.awt.image.BufferedImage;
import java.util.Map;

@SuppressWarnings("unused")
public class PictureFileInMemoryRepository implements PictureFileRepository {

    @Value("server.port")
    private String serverPort;
    @Value("server.address")
    private String serverAddress;

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
        if(picturesMap.containsKey(uniqueKey)) {
            return "http://" + serverAddress + ":" + serverPort
                    + "/local-pictures/" + uniqueKey;
        } else {
            throw new PictureFileIOException(
                    new Exception()
            );
        }
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

    public Map<String, Pair<BufferedImage, String>> getPicturesMap() {
        return picturesMap;
    }
}

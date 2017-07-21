package com.weframe.picture.service.impl;

import com.weframe.picture.service.PictureFileRepository;
import com.weframe.picture.service.exception.PictureFileIOException;
import org.springframework.data.util.Pair;

import java.awt.image.BufferedImage;
import java.util.Map;

@SuppressWarnings("unused")

public class PictureFileInMemoryRepository implements PictureFileRepository {

    private final String serverPort;
    private final String serverAddress;
    private final Map<String, Pair<BufferedImage, String>> picturesMap;

    public PictureFileInMemoryRepository(final Map<String, Pair<BufferedImage, String>> picturesMap, final String serverAddress, final String serverPort) {
        this.picturesMap = picturesMap;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
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

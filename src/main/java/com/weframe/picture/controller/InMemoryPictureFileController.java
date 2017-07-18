package com.weframe.picture.controller;

import com.weframe.picture.service.impl.PictureFileInMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/local-pictures")
@CrossOrigin
@Profile("embedded")
public class InMemoryPictureFileController {

    private final PictureFileInMemoryRepository repository;

    public InMemoryPictureFileController(final PictureFileInMemoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{pictureId}", method = RequestMethod.GET)
    private void getPictureImage(@PathVariable("pictureId") final String pictureId,
                                 final HttpServletResponse response) throws IOException {
        Pair<BufferedImage, String> image = repository.getPicturesMap().get(pictureId);
        if(image != null) {
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image.getFirst(), image.getSecond(), jpegOutputStream);
            byte[] imgByte = jpegOutputStream.toByteArray();
            preparePictureResponse(response, imgByte, image.getSecond());
        } else {
            prepareEmptyResponse(response);
        }
    }

    private void preparePictureResponse(final HttpServletResponse response,
                                        final byte[] imgByte,
                                        final String formatName) throws IOException {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/" + formatName);
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(imgByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    private void prepareEmptyResponse(final HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}

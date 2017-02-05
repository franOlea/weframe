package com.weframe.controller;

import com.weframe.picture.service.PictureService;
import com.weframe.picture.service.exceptions.PicturePutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class PictureController {

    private static String tempDir;
    private static String storageUnit;
    private static String fileKey;
    private PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/pictures/")
    public ResponseEntity<String> uploadPicture(@RequestParam("file") MultipartFile multipartFile) {
        try {
            File pictureFile = new File(tempDir + multipartFile.getName().hashCode());
            multipartFile.transferTo(pictureFile);
            pictureService.putPictureFile(storageUnit, fileKey, pictureFile);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (PicturePutException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

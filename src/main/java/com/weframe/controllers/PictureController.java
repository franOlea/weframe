package com.weframe.controllers;

import com.weframe.picture.service.PictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class PictureController {

    private final PictureService service;


    public PictureController(final PictureService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity handleFileUpload(@RequestParam("file")final MultipartFile file) {
        return null;
    }
}

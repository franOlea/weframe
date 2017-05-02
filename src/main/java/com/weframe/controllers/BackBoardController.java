package com.weframe.controllers;

import com.weframe.product.model.generic.BackBoard;
import com.weframe.product.service.impl.BackBoardService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
@RestController
@RequestMapping("/backboards")
@CrossOrigin
public class BackBoardController {

    private static final Logger logger = Logger.getLogger(BackBoardController.class);
    
    private final BackBoardService backBoardService;
    private final ResponseGenerator<BackBoard> responseGenerator;

    public BackBoardController(final BackBoardService backBoardService,
                               final ResponseGenerator<BackBoard> responseGenerator) {
        this.backBoardService = backBoardService;
        this.responseGenerator = responseGenerator;
    }

    private ResponseEntity<Collection<BackBoard>> generateResponse(
            final Collection<BackBoard> backBoards) {
        return new ResponseEntity<>(backBoards, new HttpHeaders(), HttpStatus.OK);
    }
    
}

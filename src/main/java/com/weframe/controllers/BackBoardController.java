package com.weframe.controllers;


import com.weframe.product.service.exception.InvalidGenericProductPersistenceException;
import com.weframe.product.service.impl.BackBoardService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backboards")
@CrossOrigin
public class BackBoardController {

    private static final Logger logger = Logger.getLogger(BackBoardController.class);

    private final BackBoardService service;

    public BackBoardController(final BackBoardService service) {
        this.service = service;
    }

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    private ResponseEntity getBackBoards(
//            @RequestParam(value="page", defaultValue="0", required = false) final int page,
//            @RequestParam(value="size", defaultValue = "10", required = false) final int size) {
//        try {
//            return service.getAll(page, size);
//        } catch (InvalidGenericProductPersistenceException e) {
//            e.printStackTrace();
//        }
//    }

}

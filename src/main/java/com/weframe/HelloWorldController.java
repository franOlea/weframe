package com.weframe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    ResponseEntity<String> helloWolrd() {
        return new ResponseEntity<String>("Hello World!", HttpStatus.OK);
    }

}

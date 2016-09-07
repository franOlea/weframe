package com.weframe;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HelloWorldRestController {

    @RequestMapping("/helloWorld")
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }

    public class HelloWorld {
        public String text = "Hello world!";
        public UUID id = UUID.randomUUID();
    }
}

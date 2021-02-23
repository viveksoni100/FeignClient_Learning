package com.example.service2.service2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author viveksoni100
 */
@RestController
public class Service2Controller {

    @GetMapping
    public String welcome() {
        return "Welcome to Service2";
    }
}

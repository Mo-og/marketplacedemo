package com.example.marketplacedemo.controllers;

import com.example.marketplacedemo.IllegalRequestInputException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("rest/test")
    public String testException() {
        throw new RuntimeException("Some message of RuntimeException");
    }

    @GetMapping("rest/test1")
    public String test1Exception() {
        throw new IllegalRequestInputException("This is a test IllegalRequestException message");
    }
}

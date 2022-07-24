package com.example.marketplacedemo.controllers;

import com.example.marketplacedemo.IllegalRequestInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
/*
    //redirects to error page
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String toCustomPage(Exception ex, Model model) {
        model.addAttribute("error", ex);
        return "error";
    }*/

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> all(Exception ex) {
        logger.error("Exception in controller", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Please contact administrator.\nError message: " + ex.getMessage());
    }

    //returns message
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalRequestInputException.class)
    public ResponseEntity<String> invalidRequestBody(Exception ex) {
        logger.debug("Incorrect user data", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}

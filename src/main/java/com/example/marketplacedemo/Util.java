package com.example.marketplacedemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

public abstract class Util {
    public static ResponseEntity<HashMap<String, String>> createErrorsMapResponse(BindingResult result) {
        if (result.hasErrors()) {
            var ErrorsMap = new HashMap<String, String>();
            for (FieldError e : result.getFieldErrors())
                ErrorsMap.put(e.getField(), e.getDefaultMessage());
            return new ResponseEntity<>(ErrorsMap, HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }

    public static <T> ResponseEntity<?> ok(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public static ResponseEntity<?> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

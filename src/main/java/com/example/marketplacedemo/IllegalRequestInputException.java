package com.example.marketplacedemo;

public class IllegalRequestInputException extends RuntimeException {
    public IllegalRequestInputException(String message) {
        super(message);
    }
}
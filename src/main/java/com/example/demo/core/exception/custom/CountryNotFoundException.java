package com.example.demo.core.exception.custom;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(Long id){
        super(String.format("City with Id %d not found", id));
    }
}

package com.example.demo.core.exception;

import org.springframework.http.HttpStatus;

public class NoDataFoundException  extends BaseException{

    private static HttpStatus NOT_FOUND  = HttpStatus.NOT_FOUND;

    public NoDataFoundException() {
        super(NOT_FOUND.getReasonPhrase());
    }

    public  HttpStatus getStatus() {
        return NOT_FOUND;
    }

    public int getCode(){
        return NOT_FOUND.value();
    }

    public String getMessage(){
        return NOT_FOUND.getReasonPhrase();
    }
}

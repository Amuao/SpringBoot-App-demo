package com.example.demo.core.exception;

public class NoDataFoundException  extends RuntimeException{

    public NoDataFoundException() {
        super("No data found");
    }
}

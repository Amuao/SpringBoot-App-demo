package com.example.demo.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException {

    private HttpStatus SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseException(String message){
        super(message);
    }

    public  HttpStatus getStatus() {
        return SERVER_ERROR;
    }

    public int getCode(){
        return SERVER_ERROR.value();
    }

    public String getMessage(){
        return SERVER_ERROR.getReasonPhrase();
    }
}

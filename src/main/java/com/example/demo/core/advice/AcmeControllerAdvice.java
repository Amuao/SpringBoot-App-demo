package com.example.demo.core.advice;

import com.example.demo.core.base.Result;
import com.example.demo.core.exception.BaseException;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AcmeControllerAdvice extends ResponseEntityExceptionHandler {

    //对HTTP请求参数进行处理
    @InitBinder
    public void handleException(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    //自定义异常重新封装
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(
            BaseException ex, WebRequest request) {
        Result fail = Result.fail(String.valueOf(ex.getCode()), null, ex.getMessage());
        return new ResponseEntity<>(fail, ex.getStatus());
    }

    //重写Valid参数校验
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        Result fail = Result.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), null, errors.toString());
        return new ResponseEntity<>(fail, HttpStatus.BAD_REQUEST);
    }
}

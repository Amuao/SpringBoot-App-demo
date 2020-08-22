package com.example.demo.core.base;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class Result<T> {
    public static String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    public static String SUCCESS_MESSAGE = "操作成功";

    //操作失败
    private static String FAIL_CODE = "7001";
    private String code;
    private T result;
    private String message;
    private LocalDateTime timestamp ;

    public static Result success(Object result) {
        return build(SUCCESS_CODE, result, SUCCESS_MESSAGE);
    }

    public static Result successWithMessage(Object result, String message) {
        return build(SUCCESS_CODE, result, message);
    }

    public static Result fail(String code, Object result, String message) {
        return build(code, result, message);
    }

    public static Result build(String code, Object result, String message) {
        return Result.builder().code(code).result(result).message(message).timestamp(LocalDateTime.now()).build();
    }
}

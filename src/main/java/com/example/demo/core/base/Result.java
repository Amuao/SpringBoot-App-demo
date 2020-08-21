package com.example.demo.core.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    public static String SUCCESS_CODE = "200";
    public static String SUCCESS_MESSAGE = "操作成功";

    private static String FAIL_CODE = "400";
    private String code;
    private T result;
    private String message;

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
        return Result.builder().code(code).result(result).message(message).build();
    }
}

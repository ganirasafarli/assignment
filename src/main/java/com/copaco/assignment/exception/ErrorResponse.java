package com.copaco.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private int code;
    private String message;

    public static ErrorResponse instance(int code, String message) {
        return new ErrorResponse(code, message);
    }
}

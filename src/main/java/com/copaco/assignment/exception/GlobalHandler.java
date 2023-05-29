package com.copaco.assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(NotFoundError.class)
    public ResponseEntity<?> handleQuoteNotFoundException(NotFoundError e) {
        log.error("Exception occurred : {}", e.getMessage());
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ErrorResponse.instance(statusCode.value(), e.getMessage()), statusCode);
    }
}

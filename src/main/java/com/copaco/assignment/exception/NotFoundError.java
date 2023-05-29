package com.copaco.assignment.exception;

public class NotFoundError extends RuntimeException{
    public NotFoundError(String message){
        super(message);
    }
}

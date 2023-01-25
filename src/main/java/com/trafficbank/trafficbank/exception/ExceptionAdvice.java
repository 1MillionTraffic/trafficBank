package com.trafficbank.trafficbank.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;


@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(HttpStatusCodeException.class)
    public Object requestBodyIsWrong(HttpStatusCodeException e) {
        return Map.of("error", e.getMessage());
    }

}

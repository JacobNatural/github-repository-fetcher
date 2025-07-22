package com.app.githubrepo.controller;

import com.app.githubrepo.dto.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ResponseErrorDto> httpClientErrorException(HttpClientErrorException.NotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseErrorDto(404, "User not found"));
    }
}

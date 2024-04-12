package com.thales.interview.application.controller;

import com.thales.interview.application.dto.ErrorResponseDto;
import com.thales.interview.domain.exception.ElementAlreadyExistingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponseDto handleNoSuchElementException(RuntimeException e) {

        return ErrorResponseDto.builder()
                .status(NOT_FOUND.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto handleIllegalArgumentException(RuntimeException e) {

        return ErrorResponseDto.builder()
                .status(BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ElementAlreadyExistingException.class)
    public ErrorResponseDto handleElementAlreadyExistingException(RuntimeException e) {

        return ErrorResponseDto.builder()
                .status(CONFLICT.value())
                .message(e.getMessage())
                .build();
    }
}

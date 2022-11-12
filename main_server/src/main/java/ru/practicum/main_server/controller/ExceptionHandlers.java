package ru.practicum.main_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main_server.dto.ApiError;
import ru.practicum.main_server.exception.ObjectNotFoundException;
import ru.practicum.main_server.exception.WrongRequestException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {
    static final String REASON_MESSAGE = "object not found";

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(RuntimeException e) {
        return ApiError.builder()
                .errors(Arrays.stream(e.getStackTrace()).map(error -> error.toString()).collect(Collectors.toList()))
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .reason(REASON_MESSAGE)
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(WrongRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError forbidden(RuntimeException e) {
        return ApiError.builder()
                .errors(Arrays.stream(e.getStackTrace()).map(error -> error.toString()).collect(Collectors.toList()))
                .status(String.valueOf(HttpStatus.FORBIDDEN))
                .reason(REASON_MESSAGE)
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError requestErrors(IllegalArgumentException e){
        return ApiError.builder()
                .errors(Arrays.stream(e.getStackTrace()).map(error -> error.toString()).collect(Collectors.toList()))
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .reason(REASON_MESSAGE)
                .message("validation error, " + e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validHandle(MethodArgumentNotValidException e){
        return ApiError.builder()
                .errors(Arrays.stream(e.getStackTrace()).map(error -> error.toString()).collect(Collectors.toList()))
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .reason(REASON_MESSAGE)
                .message("validation error, " + e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}

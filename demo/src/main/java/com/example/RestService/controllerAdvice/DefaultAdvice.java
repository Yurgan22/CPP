package com.example.RestService.controllerAdvice;

import com.example.RestService.exceptions.CalculationException;
import com.example.RestService.responses.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;


@RestControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CalculationException.class)
    public ResponseEntity<Response> handleException(@NotNull CalculationException e) {
        logger.error("ERROR CODE 400", e);
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleException(@NotNull IllegalArgumentException e) {
        logger.error("ERROR CODE 400",e);
        return new ResponseEntity<>(new Response(e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(@NotNull Exception e) {
        logger.error("ERROR CODE 500", e);
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Response> handlerException(@NotNull PersistenceException e){
        logger.error("Persistence exception");

        return new ResponseEntity<>(new Response(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleException(@NotNull MethodArgumentTypeMismatchException e){
        logger.error("FAILED FORMAT PARAMETER",e);
        return new ResponseEntity<>(new Response(e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}


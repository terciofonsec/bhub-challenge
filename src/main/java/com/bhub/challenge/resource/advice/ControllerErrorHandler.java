package com.bhub.challenge.resource.advice;

import com.bhub.challenge.service.exceptions.ResourceAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ArrayList<String> messages = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            messages.add(String.format("%s - %s", error.getField(), error.getDefaultMessage()));
        }
        ErrorResponse errorResponse = new ErrorResponse(messages);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> handleResourceAlreadyExists(Exception ex) {
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()));
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}

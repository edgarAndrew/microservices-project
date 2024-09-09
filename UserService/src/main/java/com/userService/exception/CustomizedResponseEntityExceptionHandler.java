package com.userService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Global error handler
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // User not found handler
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Duplicate User handler
    @ExceptionHandler(DuplicateUserException.class)
    public final ResponseEntity<ErrorDetails> handleDuplicateUserException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Duplicate User handler
    @ExceptionHandler(InvalidRoleException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidRoleException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Duplicate User handler
    @ExceptionHandler(InvalidCredentialsException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidCredentialsException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

}

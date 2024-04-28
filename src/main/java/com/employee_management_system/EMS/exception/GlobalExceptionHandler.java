package com.employee_management_system.EMS.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidDtoException.class)
    public final ResponseEntity<?> handleInvalidDtoException(Exception ex,WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleEntityException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), System.currentTimeMillis(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleRuntimeException(Exception ex,WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public final ResponseEntity<?> handleJwtException(Exception ex, WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
                System.currentTimeMillis(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

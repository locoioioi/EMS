package com.employee_management_system.EMS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidDtoException.class)
    public final ResponseEntity<?> handleProjectTaskException(Exception ex,WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleRuntimeException(Exception ex,WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

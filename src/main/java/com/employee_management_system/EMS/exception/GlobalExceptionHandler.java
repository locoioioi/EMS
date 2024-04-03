package com.employee_management_system.EMS.exception;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handles exceptions when an invalid data transfer object (DTO) is received. It creates a detailed error response
     * that includes the error message, the current time, and the request details, then sends it back with a 400 BAD_REQUEST status.
     *
     * @param ex the caught {@link InvalidDtoException}
     * @param request provides context about the web request that resulted in the exception
     * @return a {@code ResponseEntity} object containing the error details and the HTTP status code 400 (BAD_REQUEST)
     */
    @ExceptionHandler(InvalidDtoException.class)
    public final ResponseEntity<?> handleInvalidDtoException(Exception ex,WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches {@link EntityNotFoundException} and creates a response with details about the error.
     * This includes the exception message, timestamp of occurrence, and a summary of the failed request.
     * The response is returned with a 404 NOT_FOUND status, indicating the requested resource was not found.
     *
     * @param ex the caught {@link EntityNotFoundException}
     * @param request details about the web request that resulted in the exception
     * @return a {@link ResponseEntity} containing the error information with a NOT_FOUND (404) status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleEntityException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), System.currentTimeMillis(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all uncaught exceptions, providing a generic response for unexpected errors.
     * This method captures runtime exceptions, logs their details, and constructs a response
     * with the error message, the current timestamp, and a brief description of the request that led to the error.
     * It ensures a consistent error response format across the application for any unhandled exceptions,
     * returning a 500 INTERNAL_SERVER_ERROR status to indicate a server-side problem.
     *
     * @param ex the exception that was caught
     * @param request the web request during which the exception occurred
     * @return a {@code ResponseEntity} with error details and an INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleRuntimeException(Exception ex,WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Overrides the default Spring MVC handler for method argument validation errors.
     * When a controller method parameter annotated with {@link Valid} fails validation,
     * this method constructs a detailed response highlighting the specific validation error,
     * along with a timestamp and a description of the web request that caused the validation failure.
     * This custom response is then returned with a 400 BAD_REQUEST status, indicating a client error.
     *
     * @param ex the exception that captures the validation errors
     * @param headers HTTP headers for the response
     * @param status the HTTP status code to be used in the response (typically BAD_REQUEST)
     * @param request the web request during which the exception occurred
     * @return a {@link ResponseEntity} containing the custom error details and BAD_REQUEST status
     */
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

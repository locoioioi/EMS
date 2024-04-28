package com.employee_management_system.EMS.exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}

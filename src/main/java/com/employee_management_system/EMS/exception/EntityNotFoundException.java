package com.employee_management_system.EMS.exception;

public class EntityNotFoundException extends RuntimeException {
    private Class<?> zclass;
    private String id;

    public EntityNotFoundException(Class<?> zclass, String customMessage) {
        super(String.format("%s with %s not found", zclass.getSimpleName(), customMessage));
    }
}

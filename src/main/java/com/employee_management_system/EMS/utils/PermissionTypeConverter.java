package com.employee_management_system.EMS.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;
@Converter(autoApply = true)
public class PermissionTypeConverter implements AttributeConverter<PermissionType,String> {
    @Override
    public String convertToDatabaseColumn(PermissionType type) {
        return type.getType();
    }

    @Override
    public PermissionType convertToEntityAttribute(String value) {
        if (value == null) return null;
        return Stream
                .of(PermissionType.values())
                .filter(permissionType -> permissionType.getType().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}

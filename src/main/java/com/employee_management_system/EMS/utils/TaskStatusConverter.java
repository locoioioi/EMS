package com.employee_management_system.EMS.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus,String> {
    @Override
    public String convertToDatabaseColumn(TaskStatus status) {
        return status.getStatus();
    }

    @Override
    public TaskStatus convertToEntityAttribute(String value) {
        if (value == null) return null;
        return Stream
                .of(TaskStatus.values())
                .filter(taskStatus -> taskStatus.getStatus().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}

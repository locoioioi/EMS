package com.employee_management_system.EMS.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProjectStatusConverter implements AttributeConverter<ProjectStatus,String> {
    @Override
    public String convertToDatabaseColumn(ProjectStatus projectStatus) {
        return projectStatus.getState();
    }

    @Override
    public ProjectStatus convertToEntityAttribute(String state) {
        if (state == null) {
            return null;
        }
        return Stream
                .of(ProjectStatus.values())
                .filter(projectStatus -> projectStatus.getState().equals(state))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}

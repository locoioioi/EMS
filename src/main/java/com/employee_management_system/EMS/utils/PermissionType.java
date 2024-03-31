package com.employee_management_system.EMS.utils;

import lombok.Getter;

@Getter
public enum PermissionType {
    View("view"), Edit("edit"), Comment("comment");

    private final String type;

    PermissionType(String type) {
        this.type = type;
    }
}

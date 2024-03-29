package com.employee_management_system.EMS.utils;

import lombok.Getter;

@Getter
public enum ProjectStatus {
    INITIALIZE("initialize"), ONGOING("ongoing"), FINISH("finish");

    private String state;
    ProjectStatus(String state) {
        this.state = state;
    }
}

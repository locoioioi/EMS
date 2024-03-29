package com.employee_management_system.EMS.utils;

import lombok.Getter;

@Getter
public enum TaskStatus {
    Working("working"), Finish("Finish");

    private String status;

    TaskStatus(String status) {
        this.status = status;
    }

}

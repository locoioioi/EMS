package com.employee_management_system.EMS.dto.project;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private int projectId;
    private String name;
    private String status;
    private LocalDateTime startDate;
    private int employeeId;
    private int numberOfEmployees;
    private int departmentId;
    private int numberOfTasks;
}



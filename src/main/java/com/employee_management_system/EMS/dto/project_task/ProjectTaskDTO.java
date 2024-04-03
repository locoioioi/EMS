package com.employee_management_system.EMS.dto.project_task;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTaskDTO {
    private int projectTaskId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private String status;
    private int employeeId;
    private int projectId;
}

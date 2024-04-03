package com.employee_management_system.EMS.dto.project_permission;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPermissionDTO {
    private int projectPermissionId;
    private String PermissionType;
    private int employeeId;
    private int projectId;
}

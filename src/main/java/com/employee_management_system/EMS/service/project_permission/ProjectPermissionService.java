package com.employee_management_system.EMS.service.project_permission;

import com.employee_management_system.EMS.dto.project_permission.ProjectPermissionDTO;

public interface ProjectPermissionService {
    ProjectPermissionDTO getProjectPermissionByEmployeeIdAndProjectId(int employeeId,int projectId);
}

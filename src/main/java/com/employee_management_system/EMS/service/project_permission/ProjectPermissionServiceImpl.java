package com.employee_management_system.EMS.service.project_permission;

import com.employee_management_system.EMS.dto.project_permission.ProjectPermissionDTO;
import com.employee_management_system.EMS.dto.project_permission.ProjectPermissionMapper;
import com.employee_management_system.EMS.entity.ProjectPermission;
import com.employee_management_system.EMS.repository.ProjectPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectPermissionServiceImpl implements ProjectPermissionService {
    private final ProjectPermissionRepository projectPermissionRepository;
    private final ProjectPermissionMapper projectPermissionMapper;

    @Override
    public ProjectPermissionDTO getProjectPermissionByEmployeeIdAndProjectId(int employeeId, int projectId) {
        ProjectPermission permission = projectPermissionRepository.getProjectPermissionByEmployee_IdAndProject_Id(employeeId,projectId);
        return projectPermissionMapper.toDto(permission);
    }


}

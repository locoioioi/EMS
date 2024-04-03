package com.employee_management_system.EMS.dto.project_permission;

import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.entity.ProjectPermission;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.repository.ProjectRepository;
import com.employee_management_system.EMS.utils.PermissionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectPermissionMapper {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public ProjectPermissionDTO toDto(ProjectPermission projectPermission) {
        return new ProjectPermissionDTO(
                projectPermission.getId(),
                projectPermission.getPermissionType().getType(),
                projectPermission.getEmployee().getId(),
                projectPermission.getProject().getId()
        );
    }

    public ProjectPermission toProjectPermission(ProjectPermissionDTO projecProjectPermissionDTO) {
        Employee employee = employeeRepository.findById(projecProjectPermissionDTO.getEmployeeId()).orElseThrow();
        Project project = projectRepository.findById(projecProjectPermissionDTO.getProjectId()).orElseThrow();
        PermissionType permissionType = Stream
                .of(PermissionType.values())
                .filter(
                    type -> type.getType().equals(projecProjectPermissionDTO.getPermissionType())
                )
                .findFirst()
                .orElseThrow();

        return new ProjectPermission(
                projecProjectPermissionDTO.getProjectPermissionId(),
                permissionType,
                employee,
                project
        );
    }
}

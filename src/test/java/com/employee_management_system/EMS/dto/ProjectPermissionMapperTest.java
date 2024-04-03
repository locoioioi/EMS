package com.employee_management_system.EMS.dto;

import com.employee_management_system.EMS.dto.project_permission.ProjectPermissionDTO;
import com.employee_management_system.EMS.dto.project_permission.ProjectPermissionMapper;
import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.entity.ProjectPermission;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.repository.ProjectRepository;
import com.employee_management_system.EMS.utils.PermissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectPermissionMapperTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectPermissionMapper projectPermissionMapper;
    @Captor
    ArgumentCaptor<Integer> employeeIdCapture;
    @Captor
    ArgumentCaptor<Integer> projectIdCapture;

    @BeforeEach
    void setUp() {
        projectPermissionMapper = new ProjectPermissionMapper(employeeRepository,projectRepository);
    }

    @Test
    void testToDtoSuccess() {
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        Project project = ObjectGenerator.getProject(employee,department);
        ProjectPermission permission = ObjectGenerator.getProjectPermission(employee,project);

        ProjectPermissionDTO permissionDTO = projectPermissionMapper.toDto(permission);

        assertEquals(permissionDTO.getProjectPermissionId(),permission.getId());
        assertEquals(permissionDTO.getEmployeeId(),permission.getEmployee().getId());
        assertEquals(permissionDTO.getPermissionType(),permission.getPermissionType().getType());
        assertEquals(permissionDTO.getProjectId(),permission.getProject().getId());
    }

    @Test
    void testToProjectPermissionSuccess() {
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        Project project = ObjectGenerator.getProject(employee, department);

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));

        ProjectPermissionDTO projectPermissionDTO = new ProjectPermissionDTO(
                1,
                PermissionType.Edit.getType(),
                employee.getId(),
                project.getId()
        );

        ProjectPermission projectPermission = projectPermissionMapper.toProjectPermission(projectPermissionDTO);

        verify(employeeRepository).findById(employeeIdCapture.capture());
        verify(projectRepository).findById(projectIdCapture.capture());

        assertEquals(projectPermission.getId(),projectPermissionDTO.getProjectPermissionId());
        assertEquals(projectPermission.getProject().getId(),projectPermissionDTO.getProjectId());
        assertEquals(projectPermission.getEmployee().getId(),projectPermissionDTO.getEmployeeId());
        assertEquals(projectPermission.getPermissionType().getType(),projectPermissionDTO.getPermissionType());
    }
}
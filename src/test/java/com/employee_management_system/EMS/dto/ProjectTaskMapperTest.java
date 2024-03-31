package com.employee_management_system.EMS.dto;

import com.employee_management_system.EMS.dto.project_task.ProjectTaskDTO;
import com.employee_management_system.EMS.dto.project_task.ProjectTaskMapper;
import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.entity.ProjectTask;
import com.employee_management_system.EMS.exception.InvalidDtoException;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectTaskMapperTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectTaskMapper projectTaskMapper;

    @BeforeEach
    void setUp() {
        projectTaskMapper = new ProjectTaskMapper(employeeRepository,projectRepository);
    }

    @Test
    void toDto_HappyPath() {
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        Project project = ObjectGenerator.getProject(employee, department);

        ProjectTask projectTask = ObjectGenerator.getProjectTask(employee,project);
        ProjectTaskDTO result = projectTaskMapper.toDto(projectTask);

        // Verification
        assertNotNull(result);
        assertEquals(projectTask.getId(), result.getProjectTaskId());
        assertEquals(projectTask.getName(), result.getName());
        assertEquals(projectTask.getCreatedAt(), result.getCreatedAt());
        assertEquals(projectTask.getStatus().getStatus(), result.getStatus());
        assertEquals(projectTask.getEmployee().getId(), result.getEmployeeId());
        assertEquals(projectTask.getProject().getId(), result.getProjectId());
    }

    @Test
    void toDto_WithNullEmployee_ThrowsInvalidProjectTaskException() {
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        Project project = ObjectGenerator.getProject(employee, department);

        ProjectTask projectTask = ObjectGenerator.getProjectTask(null,project);

        Exception exception = assertThrows(InvalidDtoException.class, () -> {
            projectTaskMapper.toDto(projectTask);
        });

        assertTrue(exception.getMessage().contains("Employee is null. "));
    }

    @Test
    void toDto_WithNullProject_ThrowsInvalidProjectTaskException() {
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        Project project = ObjectGenerator.getProject(employee, department);

        ProjectTask projectTask = ObjectGenerator.getProjectTask(employee,null);

        Exception exception = assertThrows(InvalidDtoException.class, () -> {
            projectTaskMapper.toDto(projectTask);
        });

        assertTrue(exception.getMessage().contains("Project is null. "));
    }

    @Test
    void toDto_WithNullStatus_ThrowsInvalidProjectTaskException() {
        // Similar setup as before but with a null Status
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        Project project = ObjectGenerator.getProject(employee, department);

        ProjectTask projectTask = ObjectGenerator.getProjectTask(employee,project);

       projectTask.setStatus(null);

        Exception exception = assertThrows(InvalidDtoException.class, () -> {
            projectTaskMapper.toDto(projectTask);
        });

        assertTrue(exception.getMessage().contains("Status is null. "));
    }

    @Test
    void toProjectTask_ValidInput_ReturnsProjectTask() {
        Employee employee = ObjectGenerator.getEmployee();
        Project project = ObjectGenerator.getProject(employee,ObjectGenerator.getDepartment(employee));
        // Given
        ProjectTaskDTO dto = new ProjectTaskDTO(1, "Task Name", LocalDateTime.now(), LocalDateTime.of(2024,4,4,11,11), "working", employee.getId(), project.getId());
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        // When
        ProjectTask result = projectTaskMapper.toProjectTask(dto);

        // Then
        assertNotNull(result);
        assertEquals(dto.getProjectTaskId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getStatus(), result.getStatus().getStatus());
        assertEquals(dto.getEmployeeId(), result.getEmployee().getId());
        assertEquals(dto.getProjectId(), result.getProject().getId());
    }

    @Test
    void toProjectTask_InvalidProjectId_ThrowsInvalidDtoException() {
        // Given
        Employee employee = ObjectGenerator.getEmployee();
        Project project = ObjectGenerator.getProject(employee,ObjectGenerator.getDepartment(employee));
        project.setId(0);
        // Given
        ProjectTaskDTO dto = new ProjectTaskDTO(1, "Task Name", LocalDateTime.now(), LocalDateTime.of(2024,4,4,11,11), "working", employee.getId(), project.getId());

        // Then
        Exception exception = assertThrows(InvalidDtoException.class, () -> {
            // When
            projectTaskMapper.toProjectTask(dto);
        });

        assertTrue(exception.getMessage().contains("Project is null. "));
    }

    @Test
    void toProjectTask_InvalidEmployeeId_ThrowsInvalidDtoException() {
        // Given
        Employee employee = ObjectGenerator.getEmployee();
        employee.setId(-1);
        Project project = ObjectGenerator.getProject(employee,ObjectGenerator.getDepartment(employee));
        // Given
        ProjectTaskDTO dto = new ProjectTaskDTO(1, "Task Name", LocalDateTime.now(), LocalDateTime.of(2024,4,4,11,11), "working", employee.getId(), project.getId());

        // Then
        Exception exception = assertThrows(InvalidDtoException.class, () -> {
            // When
            projectTaskMapper.toProjectTask(dto);
        });

        assertTrue(exception.getMessage().contains("Employee is null. "));
    }

    @Test
    void toProjectTask_NullStatus_ThrowsInvalidDtoException() {
        // Given
        Employee employee = ObjectGenerator.getEmployee();
        Project project = ObjectGenerator.getProject(employee,ObjectGenerator.getDepartment(employee));
        // Given
        ProjectTaskDTO dto = new ProjectTaskDTO(1, "Task Name", LocalDateTime.now(), LocalDateTime.of(2024,4,4,11,11), "working", employee.getId(), project.getId());
        dto.setStatus(null);
        // Then
        Exception exception = assertThrows(InvalidDtoException.class, () -> {
            // When
            projectTaskMapper.toProjectTask(dto);
        });

        assertTrue(exception.getMessage().contains("Status is null. "));
    }
}
package com.employee_management_system.EMS.dto.project;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.repository.DepartmentRepository;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.utils.ProjectStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectMapperTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private ProjectMapper projectMapper;

    @BeforeEach
    void setUp() {
        projectMapper = new ProjectMapper(departmentRepository,employeeRepository);
    }

    @Test
    void toDto() {
        Employee employee = new Employee(
                1,"Loc","Truong",null,null,null,null,null,null,null);

        Department department = new Department(
                1,
                "Landmark1",
                employee,
                new HashSet<>(),
                new HashSet<>()
        );
        Project project = new Project(
                1,
                "E-commerce",
                ProjectStatus.INITIALIZE,
                LocalDateTime.now(),
                employee,
                new HashSet<>(),
                department,
                new HashSet<>(),
                new HashSet<>()
        );
        project.getEmployees().add(employee);
        department.getProjects().add(project);

        ProjectDTO dto = projectMapper.toDto(project);

        assertEquals(dto.getProjectId(),project.getId());
        assertEquals(dto.getName(),project.getName());
        assertEquals(dto.getDepartmentId(),project.getDepartment().getId());
        assertEquals(dto.getStartDate(),project.getStartDate());
        assertEquals(dto.getNumberOfEmployees(),project.getEmployees().size());
        assertEquals(dto.getNumberOfTasks(),project.getTasks().size());
    }

    @Test
    void toProject() {
        Employee employee = new Employee(
                1,"Loc","Truong",null,null,null,null,null,null,null);

        Department department = new Department(
                1,
                "Landmark1",
                employee,
                new HashSet<>(),
                new HashSet<>()
        );

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        ProjectDTO projectDTO = new ProjectDTO(
                1,
                "E-commerce",
                ProjectStatus.INITIALIZE.getState(),
                LocalDateTime.now(),
                employee.getId(),
                1,
                department.getId(),
                0
        );

        Project project = projectMapper.toProject(projectDTO);

        verify(employeeRepository).findById(employee.getId());
        verify(departmentRepository).findById(department.getId());

        assertEquals(project.getName(),projectDTO.getName());
        assertEquals(project.getTasks().size(),projectDTO.getNumberOfTasks());
        assertEquals(project.getDepartment().getId(),projectDTO.getDepartmentId());
    }
}
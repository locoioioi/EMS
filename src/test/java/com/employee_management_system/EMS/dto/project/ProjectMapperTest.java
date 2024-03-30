package com.employee_management_system.EMS.dto.project;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import com.employee_management_system.EMS.repository.DepartmentRepository;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.utils.ProjectStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static com.employee_management_system.EMS.helper.ObjectGenerator.getEmployee;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @Captor
    ArgumentCaptor<Integer> employeeIdCaptor;
    @Captor
    ArgumentCaptor<Integer> departmentIdCaptor;

    @BeforeEach
    void setUp() {
        projectMapper = new ProjectMapper(departmentRepository,employeeRepository);
    }

    @Test
    void toDto() {
        Employee employee = getEmployee();

        Department department = ObjectGenerator.getDepartment(employee);

        Project project = ObjectGenerator.getProject(employee,department);

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
        Employee employee = ObjectGenerator.getEmployee();

        Department department = ObjectGenerator.getDepartment(employee);

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));

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

        verify(employeeRepository).findById(employeeIdCaptor.capture());
        verify(departmentRepository).findById(departmentIdCaptor.capture());

        assertEquals(employee.getId(), employeeIdCaptor.getValue().longValue());
        assertEquals(department.getId(), departmentIdCaptor.getValue().longValue());
        assertEquals(project.getName(),projectDTO.getName());
        assertEquals(project.getTasks().size(),projectDTO.getNumberOfTasks());
        assertEquals(project.getDepartment().getId(),projectDTO.getDepartmentId());
        assertEquals(project.getEmployees().size(),projectDTO.getNumberOfEmployees());
        assertEquals(project.getStatus().getState(),projectDTO.getStatus());
    }
}
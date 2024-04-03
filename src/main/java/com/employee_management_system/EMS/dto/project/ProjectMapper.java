package com.employee_management_system.EMS.dto.project;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.repository.DepartmentRepository;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.utils.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectMapper {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public ProjectDTO toDto(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getStatus().getState(),
                project.getStartDate(),
                project.getEmployee().getId(),
                project.getEmployees().size(),
                project.getDepartment().getId(),
                project.getTasks().size()
        );
    }

    public Project toProject(ProjectDTO projectDTO) {
        Department department = departmentRepository.findById(projectDTO.getDepartmentId()).orElseThrow(); // ? add exception later
        Employee employee = employeeRepository.findById(projectDTO.getEmployeeId()).orElseThrow(); // ? add exception later
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        return new Project(
                0,
                projectDTO.getName(),
                ProjectStatus.INITIALIZE,
                LocalDateTime.now(),
                employee,
                employees,
                department,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}

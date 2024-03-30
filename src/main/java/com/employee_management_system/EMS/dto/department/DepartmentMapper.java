package com.employee_management_system.EMS.dto.department;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
@Service
@RequiredArgsConstructor
public class DepartmentMapper {
    private final EmployeeRepository employeeRepository;

    public DepartmentDTO toDTO(Department department) {
        if (department == null) return null;
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getEmployee().getId(),
                department.getEmployees().size()
        );
    }

    public Department toDepartment(CreationDepartment department) {
        Employee employee = employeeRepository.findById(department.getManagerId()).orElseThrow();
        return new Department(
                0,
                department.getName(),
                employee,
                new HashSet<>(),
                new HashSet<>()
        );
    }
}

package com.employee_management_system.EMS.dto.employee;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.DepartmentRepository;
import com.employee_management_system.EMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class EmployeeMapper {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public EmployeeDTO toDto(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUser().getId(),
                employee.getDepartment().getId()
        );
    }

    public Employee toEmployee(EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId()).orElseThrow(
                () -> new EntityNotFoundException(Department.class, "this " + employeeDTO.getDepartmentId())
        );
        User user = userRepository.findById(employeeDTO.getUserId()).orElseThrow(
                () -> new EntityNotFoundException(User.class, "this " + employeeDTO.getUserId())
        );

        return new Employee(
                0,
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                user,
                null,
                null,
                department,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}

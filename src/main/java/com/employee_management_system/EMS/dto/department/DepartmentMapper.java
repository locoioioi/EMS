package com.employee_management_system.EMS.dto.department;

import com.employee_management_system.EMS.entity.Department;

public class DepartmentMapper {
    public DepartmentDTO toDTO(Department department) {
        if (department == null) return null;
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getEmployee().getId(),
                department.getEmployees().size()
        );
    }
}

package com.employee_management_system.EMS.dto.department;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private int departmentId;
    private String name;
    private int managerId;
    private int numberOfEmployees;
}

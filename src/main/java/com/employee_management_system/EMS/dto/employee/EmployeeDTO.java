package com.employee_management_system.EMS.dto.employee;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private int employeeID;
    private String firstName;
    private String lastName;
    private int userId;
    private int departmentId;
}

package com.employee_management_system.EMS.dto.project;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreationProject {
    private String name;
    private int employeeId;
    private int departmentId;
}

package com.employee_management_system.EMS.dto.department;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreationDepartment {
    private String name;
    private int managerId;
}

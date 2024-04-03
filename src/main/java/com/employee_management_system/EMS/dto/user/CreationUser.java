package com.employee_management_system.EMS.dto.user;

import com.employee_management_system.EMS.dto.employee.EmployeeDTO;
import com.employee_management_system.EMS.dto.role.RoleDTO;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreationUser {
    private int userId;
    private String username;
    private String password;
    private String email;
    private EmployeeDTO employee;
    private List<RoleDTO> roles;
}

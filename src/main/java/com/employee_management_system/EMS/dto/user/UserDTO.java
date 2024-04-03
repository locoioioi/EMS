package com.employee_management_system.EMS.dto.user;

import com.employee_management_system.EMS.dto.role.RoleDTO;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String username;
    private int employeeId;
    private List<RoleDTO> roleDTOS;
}

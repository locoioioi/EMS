package com.employee_management_system.EMS.dto.role;

import com.employee_management_system.EMS.entity.Role;
import com.employee_management_system.EMS.exception.InvalidDtoException;

import java.util.HashSet;

public class RoleMapper {
    public RoleDTO toDto(Role role) {
        if (role.getName() == null || role.getName().isEmpty()) {
            throw new InvalidDtoException("invalid role instance");
        }

        return new RoleDTO(
                role.getId(),
                role.getName()
        );
    }

    public Role toRole(RoleDTO roleDTO) {
        return new Role(
                0,
                roleDTO.getName(),
                new HashSet<>()
        );
    }
}

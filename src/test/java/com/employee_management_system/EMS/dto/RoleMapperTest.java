package com.employee_management_system.EMS.dto;

import com.employee_management_system.EMS.dto.role.RoleDTO;
import com.employee_management_system.EMS.dto.role.RoleMapper;
import com.employee_management_system.EMS.entity.Role;
import com.employee_management_system.EMS.exception.InvalidDtoException;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RoleMapperTest {
    @InjectMocks
    private RoleMapper roleMapper;

    @BeforeEach
    void setUp() {
        roleMapper = new RoleMapper();
    }

    @Test
    void testToRoleDTO_Success() {
        Role role = ObjectGenerator.getRole();
        RoleDTO roleDTO = roleMapper.toDto(role);

        assertEquals(role.getId(),roleDTO.getRoleId());
        assertEquals(role.getName(),roleDTO.getName());
    }
    @Test
    void testToRoleDTO_NameNull() {
        Role role = ObjectGenerator.getRole();
        role.setName(null);

        assertThrows(InvalidDtoException.class, () -> roleMapper.toDto(role));
    }

    void testToRole_Success() {
        RoleDTO roleDTO = new RoleDTO(1,"Employee");

        Role role = roleMapper.toRole(roleDTO);
        assertEquals(role.getName(),roleDTO.getName());
    }
}

package com.employee_management_system.EMS.dto;

import com.employee_management_system.EMS.dto.employee.EmployeeDTO;
import com.employee_management_system.EMS.dto.employee.EmployeeMapper;
import com.employee_management_system.EMS.dto.role.RoleDTO;
import com.employee_management_system.EMS.dto.role.RoleMapper;
import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.dto.user.UserDTO;
import com.employee_management_system.EMS.dto.user.UserMapper;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Role;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.InvalidDtoException;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import com.employee_management_system.EMS.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @Mock
    private RoleMapper roleMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserMapper userMapper;
    @Test
    void testToDto() {
        // Given
        User user = ObjectGenerator.getUser();
        Role role = ObjectGenerator.getRole();
        Employee employee = ObjectGenerator.getEmployee();
        user.setRoles(List.of(role));
        user.setEmployeeInformation(employee);
        employee.setUser(user);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(role.getId());
        roleDTO.setName(role.getName());


        when(roleMapper.toDto(role)).thenReturn(roleDTO);

        // When
        UserDTO userDTO = userMapper.toDto(user);

        // Then
        assertEquals(user.getId(), userDTO.getUserId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmployeeInformation().getId(), userDTO.getEmployeeId());
        assertEquals(1, userDTO.getRoleDTOS().size());
        assertEquals(role.getName(), userDTO.getRoleDTOS().get(0).getName());
    }

    @Test
    void testToUserWithNullEmployee() {
        // Given
        CreationUser creationUser = new CreationUser();
        creationUser.setUserId(1);
        creationUser.setUsername("testuser");
        creationUser.setPassword("password");
        creationUser.setEmail("test@example.com");
        creationUser.setEmployee(null); // Null employee

        // When, Then
        assertThrows(InvalidDtoException.class, () -> userMapper.toUser(creationUser));
    }

    @Test
    void testToUserWithEmptyRoles() {
        // Given
        CreationUser creationUser = new CreationUser();
        creationUser.setUserId(1);
        creationUser.setUsername("testuser");
        creationUser.setPassword("password");
        creationUser.setEmail("test@example.com");
        creationUser.setRoles(Collections.emptyList()); // Empty roles list

        // When, Then
        assertThrows(InvalidDtoException.class, () -> userMapper.toUser(creationUser));
    }

    @Test
    void testToUser() {
        // Given
        CreationUser creationUser = new CreationUser();
        creationUser.setUserId(1);
        creationUser.setUsername("testuser");
        creationUser.setPassword("password");
        creationUser.setEmail("test@example.com");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeID(1);

        when(employeeMapper.toEmployee(employeeDTO)).thenReturn(new Employee());

        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setRoleId(1);
        roleDTO1.setName("ROLE_ADMIN");

        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO2.setRoleId(2);
        roleDTO2.setName("ROLE_EMPLOYEE");

        List<RoleDTO> roleDTOs = Arrays.asList(roleDTO1, roleDTO2);

        Iterable<Integer> roleIds = roleDTOs.stream().map(RoleDTO::getRoleId).toList();

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_EMPLOYEE");
        when(roleRepository.findAllById(roleIds)).thenReturn(Arrays.asList(role1, role2));
        creationUser.setEmployee(employeeDTO);
        creationUser.setRoles(roleDTOs);
        // When
        User user = userMapper.toUser(creationUser);

        // Then
        assertEquals(creationUser.getUserId(), user.getId());
        assertEquals(creationUser.getUsername(), user.getUsername());
        assertEquals(creationUser.getEmail(), user.getEmail());
        assertEquals(2, user.getRoles().size());
    }
}

package com.employee_management_system.EMS.dto.user;

import com.employee_management_system.EMS.dto.employee.EmployeeMapper;
import com.employee_management_system.EMS.dto.role.RoleDTO;
import com.employee_management_system.EMS.dto.role.RoleMapper;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Role;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.InvalidDtoException;
import com.employee_management_system.EMS.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;
    private final EmployeeMapper employeeMapper;
    private final RoleRepository roleRepository;
    public UserDTO toDto(User user) {
        System.out.println("here");
        List<RoleDTO> roleDTOS = user.getRoles().stream().map(roleMapper::toDto).toList();
        System.out.println(user.getEmployeeInformation().getId());
        System.out.println("here");
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmployeeInformation().getId(),
                roleDTOS
        );
    }

    public User toUser(CreationUser user) {
        if (user.getEmployee() == null) {
            throw new InvalidDtoException("Invalid user option. An Employee is required");
        }
        if (user.getRoles().isEmpty()) {
            throw new InvalidDtoException("Invalid user option. A List of roles are required");
        }
        Iterable<Integer> roleIds = user.getRoles().stream().map(RoleDTO::getRoleId).toList();
        List<Role> roles = roleRepository.findAllById(roleIds);
        Employee employee = employeeMapper.toEmployee(user.getEmployee());

        return new User(
            0,
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            employee,
            roles
        );
    }
}

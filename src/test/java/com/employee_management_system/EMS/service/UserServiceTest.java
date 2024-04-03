package com.employee_management_system.EMS.service;

import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.dto.user.UserDTO;
import com.employee_management_system.EMS.dto.user.UserMapper;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import com.employee_management_system.EMS.repository.UserRepository;
import com.employee_management_system.EMS.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository,userMapper);
    }

    @Test
    void getUserById_UserExists_ReturnsUserDTO() {
        // Setup
        int userId = 0;
        User mockUser = ObjectGenerator.getUser();
        UserDTO expectedUserDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userMapper.toDto(mockUser)).thenReturn(expectedUserDTO);

        // Execute
        UserDTO result = userService.getUserById(userId);

        // Verify
        assertNotNull(result);
        assertEquals(expectedUserDTO, result);
        verify(userRepository).findById(userId);
        verify(userMapper).toDto(mockUser);
    }

    @Test
    void getUsers_ReturnsListOfUsers() {
        // Setup
        User mockUser1 = ObjectGenerator.getUser();
        User mockUser2 = ObjectGenerator.getUser();
        List<User> userList = Arrays.asList(mockUser1, mockUser2);
        UserDTO mockUserDTO1 = new UserDTO();
        UserDTO mockUserDTO2 = new UserDTO();
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toDto(any(User.class))).thenReturn(mockUserDTO1, mockUserDTO2);

        // Execute
        List<UserDTO> result = userService.getUsers();

        // Verify
        assertEquals(2, result.size());
        verify(userRepository).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void getUserByEmployeeId_UserExists_ReturnsUserDTO() {
        // Setup
        int employeeId = 1;
        User mockUser = ObjectGenerator.getUser();
        UserDTO expectedUserDTO = new UserDTO();

        when(userRepository.findByEmployeeInformation_Id(employeeId)).thenReturn(mockUser);
        when(userMapper.toDto(mockUser)).thenReturn(expectedUserDTO);

        // Execute
        UserDTO result = userService.getUserByEmployeeId(employeeId);

        // Verify
        assertNotNull(result);
        assertEquals(expectedUserDTO, result);
        verify(userRepository).findByEmployeeInformation_Id(employeeId);
        verify(userMapper).toDto(mockUser);
    }
}
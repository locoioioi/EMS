package com.employee_management_system.EMS.service.account;

import com.employee_management_system.EMS.dto.employee.EmployeeDTO;
import com.employee_management_system.EMS.dto.response.LoginRequest;
import com.employee_management_system.EMS.dto.response.Token;
import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.dto.user.UserDTO;
import com.employee_management_system.EMS.dto.user.UserMapper;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.ExceptionResponse;
import com.employee_management_system.EMS.repository.UserRepository;
import com.employee_management_system.EMS.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AccountServiceImpl accountService;
    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(userRepository,userMapper,authenticationManager, bCryptPasswordEncoder,jwtService);
    }

    @Test
    void login() {
        LoginRequest loginRequest = new LoginRequest("user1", "user1");
        Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        ResponseEntity<?> response = accountService.login(loginRequest);

        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() instanceof Token;
    }
    @Test
    void testLogin_Failure() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act
        ResponseEntity<?> response = accountService.login(loginRequest);

        // Assert
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() instanceof ExceptionResponse;
        ExceptionResponse exceptionResponse = (ExceptionResponse) response.getBody();
        assert exceptionResponse.getMessage().contains("login fail");
        // Assert other fields as needed
    }
    @Test
    void register() {
        // Arrange
        CreationUser creationUser = new CreationUser(1,"username12312", "password123123","asdasd@gmail.com",new EmployeeDTO(1,"asdasd","asdasd",1,1), new ArrayList<>());

        User user = new User(); // Create a User instance with appropriate data
        when(userMapper.toUser(creationUser)).thenReturn(user);
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        // Act
        ResponseEntity<?> response = accountService.register(creationUser);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(UserDTO.class, response.getBody());
    }
}
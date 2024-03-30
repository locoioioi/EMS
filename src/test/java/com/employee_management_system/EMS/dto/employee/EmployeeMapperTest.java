package com.employee_management_system.EMS.dto.employee;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import com.employee_management_system.EMS.repository.DepartmentRepository;
import com.employee_management_system.EMS.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EmployeeMapper employeeMapper;
    @Captor
    ArgumentCaptor<Integer> userIdCaptured;
    @Captor
    ArgumentCaptor<Integer> departmentIdCaptured;
    @BeforeEach
    void setUp() {
        employeeMapper = new EmployeeMapper(departmentRepository,userRepository);
    }

    @Test
    void toDto() {
        User user = ObjectGenerator.getUser();
        Employee employee = ObjectGenerator.getEmployee();
        Department department = ObjectGenerator.getDepartment(employee);
        employee.setDepartment(department);
        employee.setUser(user);
        user.setEmployeeInformation(employee);

        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        assertEquals(employeeDTO.getEmployeeID(),employee.getId());
        assertEquals(employeeDTO.getUserId(),employee.getUser().getId());
        assertEquals(employeeDTO.getDepartmentId(),employee.getDepartment().getId());
        assertEquals(employeeDTO.getLastName(),employee.getLastName());
        assertEquals(employeeDTO.getFirstName(),employee.getFirstName());
    }

    @Test
    void toEmployee() {
        User user = ObjectGenerator.getUser();
        Department department = ObjectGenerator.getDepartment(null);

        when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(department));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        EmployeeDTO employeeDTO = new EmployeeDTO(
                0,
                "Loc",
                "Truong",
                user.getId(),
                department.getId()
        );

        Employee employee = employeeMapper.toEmployee(employeeDTO);

        verify(userRepository).findById(userIdCaptured.capture());
        verify(departmentRepository).findById(departmentIdCaptured.capture());

        assertEquals(employee.getId(),0);
        assertEquals(employee.getFirstName(),employeeDTO.getFirstName());
        assertEquals(employee.getLastName(),employeeDTO.getLastName());
        assertEquals(employee.getDepartment().getId(),employeeDTO.getDepartmentId());
        assertEquals(employee.getUser().getId(),employeeDTO.getUserId());
    }
}
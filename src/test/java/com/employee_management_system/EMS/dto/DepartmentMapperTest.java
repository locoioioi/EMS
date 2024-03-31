package com.employee_management_system.EMS.dto;

import com.employee_management_system.EMS.dto.department.CreationDepartment;
import com.employee_management_system.EMS.dto.department.DepartmentDTO;
import com.employee_management_system.EMS.dto.department.DepartmentMapper;
import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentMapperTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private DepartmentMapper departmentMapper;

    @BeforeEach
    public void setUp() {
        departmentMapper = new DepartmentMapper(employeeRepository);
    }

    @Test
    void testDepartmentToDtoSuccess() {
        Employee employee = new Employee(
                1,"Loc","Truong",null,null,null,null,null,null,null);

        Department department = new Department(
                1,
                "Landmark1",
                employee,
                new HashSet<>(),
                null
        );
        employee.setDepartment(department);

        DepartmentDTO departmentDTO = departmentMapper.toDTO(department);
        assertEquals(departmentDTO.getDepartmentId(),department.getId());
        assertEquals(departmentDTO.getName(),department.getName());
        assertEquals(departmentDTO.getManagerId(),department.getEmployee().getId());
        assertEquals(departmentDTO.getNumberOfEmployees(),department.getEmployees().size());
    }

    @Test
    void testEmployeeDtoToEmployeeSuccess() {
        Employee employee = new Employee(
                1,"Loc","Truong",null,null,null,null,null,null,null);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        CreationDepartment departmentDTO = new CreationDepartment("landmark81",employee.getId());

        Department department = departmentMapper.toDepartment(departmentDTO);

        verify(employeeRepository).findById(employee.getId());

        assertEquals(departmentDTO.getName(),department.getName());
        assertEquals(departmentDTO.getManagerId(),department.getEmployee().getId());
    }
}
package com.employee_management_system.EMS.service.employee;

import com.employee_management_system.EMS.dto.employee.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    EmployeeDTO getEmployeeById(int employeeId);
    Page<EmployeeDTO> getAll(int page,int size);
    Page<EmployeeDTO> getAllByDepartmentId(int departmentId, int page, int size);
    Page<EmployeeDTO> getAllByProjectId(int projectId, int page, int size);
    boolean addEmployee(EmployeeDTO employeeDTO);
    boolean removeEmployee(int employeeId);
    EmployeeDTO updateEmployee(int employeeId,EmployeeDTO employeeDTO);
}

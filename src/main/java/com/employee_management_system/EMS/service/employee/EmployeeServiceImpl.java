package com.employee_management_system.EMS.service.employee;

import com.employee_management_system.EMS.dto.employee.EmployeeDTO;
import com.employee_management_system.EMS.dto.employee.EmployeeMapper;
import com.employee_management_system.EMS.entity.*;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO getEmployeeById(int employeeId) {
        Employee employee = getEmployee(employeeId);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeDTO> getAll(int page, int size) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, size));
        return employees.map(employeeMapper::toDto);
    }

    @Override
    public Page<EmployeeDTO> getAllByDepartmentId(int departmentId, int page, int size) {
        Page<Employee> employees = employeeRepository.getEmployeesByDepartment_Id(departmentId, PageRequest.of(page,size));
        return employees.map(employeeMapper::toDto);
    }

    @Override
    public Page<EmployeeDTO> getAllByProjectId(int projectId, int page, int size) {
        Page<Employee> employees = employeeRepository.getEmployeesByProjects_id(projectId,PageRequest.of(page,size));
        return employees.map(employeeMapper::toDto);
    }

    @Override
    public boolean addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        User user = employee.getUser();
        Department department = employee.getDepartment();

        if (user == null || department == null) {
            return false;
        }
        user.setEmployeeInformation(employee);
        department.addEmployee(employee);

        employeeRepository.saveAndFlush(employee);
        return true;
    }

    @Override
    public boolean removeEmployee(int employeeId) {
        Employee employee = getEmployee(employeeId);
        Department departmentManagement = employee.getDepartmentManagement();
        List<ProjectPermission> permissions = employee.getPermissions();
        List<Project> projects = employee.getProjects();
        User user = employee.getUser();
        Project project = employee.getProjectManagement();

        projects.forEach(
                eachProject -> eachProject.removeEmployee(employee)
        );
        departmentManagement.removeEmployee(employee);

        permissions.forEach(ProjectPermission::removeEmployee);
        user.removeEmployeeInfo();
        project.removeEmployee(employee);

        employeeRepository.delete(employee);
        return true;
    }

    @Override
    public EmployeeDTO updateEmployee(int employeeId, EmployeeDTO employeeDTO) {
        Employee employee = getEmployee(employeeId);
        Employee updated = employeeMapper.toEmployee(employeeDTO);

        employee.setDepartmentManagement(updated.getDepartmentManagement());
        employee.setFirstName(updated.getFirstName());
        employee.setLastName(updated.getLastName());
        Employee updatedEmployee = employeeRepository.saveAndFlush(employee);

        return employeeMapper.toDto(updatedEmployee);
    }

    private Employee getEmployee(int employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(
                () -> new EntityNotFoundException(Employee.class,"this " + employeeId)
        );
    }
}

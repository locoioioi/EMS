package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Page<Employee> getEmployeesByDepartment_Id(int departmentId, Pageable pageable);
    Page<Employee> getEmployeesByProjects_id(int projectId, Pageable pageable);
}

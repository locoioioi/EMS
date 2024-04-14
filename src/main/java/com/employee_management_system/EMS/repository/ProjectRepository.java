package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.dto.project.CreationProject;
import com.employee_management_system.EMS.dto.project.ProjectDTO;
import com.employee_management_system.EMS.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Page<Project> findAllByDepartment_Id(int departmentId, Pageable pageable);
    Page<Project> findAllByEmployees_employeeId(int employeeId, Pageable pageable);
}

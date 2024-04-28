package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Page<Project> findAllByDepartment_Id(int departmentId, Pageable pageable);

    @Query("SELECT p FROM Project p JOIN p.employees e WHERE e.id = :employeeId")
    Page<Project> findAllByEmployeeId(int employeeId, Pageable pageable);
}

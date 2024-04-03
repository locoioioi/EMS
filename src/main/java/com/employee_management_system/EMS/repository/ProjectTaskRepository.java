package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Integer> {
}

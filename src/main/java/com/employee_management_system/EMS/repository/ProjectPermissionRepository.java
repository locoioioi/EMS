package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.entity.ProjectPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPermissionRepository extends JpaRepository<ProjectPermission,Integer> {
}

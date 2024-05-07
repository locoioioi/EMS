package com.employee_management_system.EMS.service.project;

import com.employee_management_system.EMS.dto.project.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProjectService {
    Page<ProjectDTO> getAll(int page, int size);
    Page<ProjectDTO> getAllByDepartmentId(int departmentId, int page, int size);
    Page<ProjectDTO> getAllByEmployeeId(int employeeId, int page, int size);
    boolean addProject(ProjectDTO newProject);
    boolean updateProject(int employeeId,ProjectDTO projectDTO);
    boolean removeProject(int projectId);
    ResponseEntity<?> addEmployeeToProject(int projectId, int projectManagerId, int employeeId, String permission);
    ResponseEntity<?> removeEmployeeFromProject(int projectId, int projectManagerId, int employeeId);
}

package com.employee_management_system.EMS.service.project;

import com.employee_management_system.EMS.dto.project.ProjectDTO;
import org.springframework.data.domain.Page;

public interface ProjectService {
    Page<ProjectDTO> getAll(int page, int size);
    Page<ProjectDTO> getAllByDepartmentId(int departmentId, int page, int size);
    boolean addProject(ProjectDTO newProject);
    boolean updateProject(ProjectDTO projectDTO);
    boolean removeProject(int projectId);
    boolean addEmployeeToProject(int projectId,int projectManagerId,int employeeId);
}

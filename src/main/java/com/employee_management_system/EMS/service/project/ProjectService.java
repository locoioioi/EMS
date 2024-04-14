package com.employee_management_system.EMS.service.project;

import com.employee_management_system.EMS.dto.project.ProjectDTO;
import com.employee_management_system.EMS.dto.project_task.ProjectTaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    Page<ProjectDTO> getAll(int page, int size);
    Page<ProjectDTO> getAllByDepartmentId(int departmentId, int page, int size);
    Page<ProjectDTO> getAllByEmployeeId(int employeeId, int page, int size);
    boolean addProject(ProjectDTO newProject);
    boolean updateProject(ProjectDTO projectDTO);
    boolean removeProject(int projectId);
}

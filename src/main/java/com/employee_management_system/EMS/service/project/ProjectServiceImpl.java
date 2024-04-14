package com.employee_management_system.EMS.service.project;

import com.employee_management_system.EMS.dto.project.CreationProject;
import com.employee_management_system.EMS.dto.project.ProjectDTO;
import com.employee_management_system.EMS.dto.project.ProjectMapper;
import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.entity.ProjectTask;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.repository.ProjectRepository;
import com.employee_management_system.EMS.utils.ProjectStatus;
import com.employee_management_system.EMS.utils.ProjectStatusConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeRepository employeeRepository;
    private final ProjectStatusConverter converter;

    @Override
    public Page<ProjectDTO> getAll(int page, int size) {
        Page<Project> projects = projectRepository.findAll(PageRequest.of(page,size));
        return projects.map(projectMapper::toDto);
    }

    @Override
    public Page<ProjectDTO> getAllByDepartmentId(int departmentId, int page, int size) {
        Page<Project> projects = projectRepository.findAllByDepartment_Id(departmentId, PageRequest.of(page,size));
        return projects.map(projectMapper::toDto);
    }

    @Override
    public Page<ProjectDTO> getAllByEmployeeId(int employeeId, int page, int size) {
        Page<Project> projects = projectRepository.findAllByEmployees_employeeId(employeeId, PageRequest.of(page,size));
        return projects.map(projectMapper::toDto);
    }

    @Override
    public boolean addProject(ProjectDTO newProject) {
        Project project = projectMapper.toProject(newProject);
        if (project.getEmployee() == null) {
            return false;
        }
        project.getEmployees().forEach(
                employee -> employee.addProject(project)
        );
        project.getEmployee().setProjectManagement(project);

        projectRepository.saveAndFlush(project);
        return true;
    }

    @Override
    public boolean updateProject(ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectDTO.getProjectId()).orElseThrow(
                () -> new EntityNotFoundException(Project.class,"this " + projectDTO.getProjectId())
        );

        project.setName(projectDTO.getName());
        ProjectStatus projectStatus = converter.convertToEntityAttribute(projectDTO.getStatus());
        if (projectStatus == null) {
            return false;
        }
        project.setStatus(projectStatus);
        return true;
    }

    @Override
    public boolean removeProject(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException(Project.class,"this " + projectId)
        );
        Employee employee = project.getEmployee();
        Department department = project.getDepartment();

        employee.setProjectManagement(null);
        department.removeProject(project);

        projectRepository.delete(project);
        return true;
    }


}

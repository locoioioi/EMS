package com.employee_management_system.EMS.service.project;

import com.employee_management_system.EMS.dto.project.ProjectDTO;
import com.employee_management_system.EMS.dto.project.ProjectMapper;
import com.employee_management_system.EMS.dto.response.Response;
import com.employee_management_system.EMS.entity.*;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.repository.ProjectRepository;
import com.employee_management_system.EMS.utils.PermissionType;
import com.employee_management_system.EMS.utils.PermissionTypeConverter;
import com.employee_management_system.EMS.utils.ProjectStatus;
import com.employee_management_system.EMS.utils.ProjectStatusConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeRepository employeeRepository;
    private final ProjectStatusConverter projectStatusConverter;
    private final PermissionTypeConverter permissionTypeConverter;

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
        Page<Project> projects = projectRepository.findAllByEmployeeId(employeeId,PageRequest.of(page,size));
        return projects.map(projectMapper::toDto);
    }

    @Override
    public boolean addProject(ProjectDTO newProject) {
        Project project = projectMapper.toProject(newProject);
        if (project.getEmployee() == null) {
            return false;
        }

        project.getEmployee().setProjectManagement(project);
        System.out.println(project.getEmployees().get(0).getFirstName());
        project.getDepartment().addProject(project);
        projectRepository.saveAndFlush(project);
        return true;
    }

    @Override
    public boolean updateProject(int employeeId,ProjectDTO projectDTO) {
        // * Check existence of project.
        Project project = projectRepository.findById(projectDTO.getProjectId()).orElseThrow(
                () -> new EntityNotFoundException(Project.class,"this " + projectDTO.getProjectId())
        );
        // * Only project manager can update the status of project
        if (project.getEmployee().getId() != employeeId) {
            return false;
        }

        project.setName(projectDTO.getName());
        ProjectStatus projectStatus = projectStatusConverter.convertToEntityAttribute(projectDTO.getStatus());
        if (projectStatus == null) {
            return false;
        }
        project.setStatus(projectStatus);

        projectRepository.saveAndFlush(project);
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

    @Override
    public ResponseEntity<?> addEmployeeToProject(int projectId, int projectManagerId, int employeeId, String permission) {
        Project project = getProject(projectId);
        if (project.getEmployee().getId() != projectManagerId) {
            // * check permissionEmployee, only project manager can add employee.
            return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "This employee do not have permissionEmployee to this service", projectManagerId));
        }

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EntityNotFoundException(Employee.class, "this " + employeeId)
        );

        if (project.getEmployees().contains(employee)) {
            return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "This employee is already added to this project", null));
        }

        // * add employee to project.
        project.addEmployee(employee);
        employee.addProject(project);

        // * create permissionEmployee for employee (default permissionEmployee is view)
        PermissionType permissionType = permissionTypeConverter.convertToEntityAttribute(permission);
        if (permissionType == null) {
            return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "Permission not available", null));
        }

        ProjectPermission permissionEmployee = new ProjectPermission(
                0,
                permissionType,
                employee,
                project
        );

        project.addPermission(permissionEmployee);
        employee.addPermission(permissionEmployee);

        Project saved = projectRepository.saveAndFlush(project);
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(),"add employee successful", projectMapper.toDto(saved)));
    }

    private Project getProject(int projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException(Project.class,"this " + projectId)
        );
    }

}

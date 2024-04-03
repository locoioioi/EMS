package com.employee_management_system.EMS.dto.project_task;

import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.entity.ProjectTask;
import com.employee_management_system.EMS.exception.InvalidDtoException;
import com.employee_management_system.EMS.repository.EmployeeRepository;
import com.employee_management_system.EMS.repository.ProjectRepository;
import com.employee_management_system.EMS.utils.ProjectStatus;
import com.employee_management_system.EMS.utils.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectTaskMapper {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    public ProjectTaskDTO toDto(ProjectTask projectTask){
        String errorMessage = "Invalid ProjectTask object: ";
        if (projectTask.getEmployee() == null) {
            errorMessage += "Employee is null. ";
        } else if (projectTask.getProject() == null) {
            errorMessage += "Project is null. ";
        } else if (projectTask.getStatus() == null) {
            errorMessage += "Status is null. ";
        }

        if (errorMessage.contains("null")) {
            throw new InvalidDtoException(errorMessage);
        }

        return new ProjectTaskDTO(
                projectTask.getId(),
                projectTask.getName(),
                projectTask.getCreatedAt(),
                projectTask.getDueDate(),
                projectTask.getStatus().getStatus(),
                projectTask.getEmployee().getId(),
                projectTask.getProject().getId()
        );
    }

    public ProjectTask toProjectTask(ProjectTaskDTO projectTaskDTO) {
        String errorMessage = "Invalid ProjectTaskDTO object: ";
        if (projectTaskDTO.getProjectId() < 1) {
            errorMessage += "Project is null. ";
        } else if (projectTaskDTO.getEmployeeId() < 1) {
            errorMessage += "Employee is null. ";
        } else if (projectTaskDTO.getStatus() == null) {
            errorMessage += "Status is null. ";
        }

        if (errorMessage.contains("null")) {
            throw new InvalidDtoException(errorMessage);
        }

        TaskStatus status = Stream.of(TaskStatus.values()).filter(projectStatus -> projectStatus.getStatus().equals(projectTaskDTO.getStatus())).findFirst().orElseThrow();
        Employee employee = employeeRepository.findById(projectTaskDTO.getEmployeeId()).orElseThrow();
        Project project = projectRepository.findById(projectTaskDTO.getProjectId()).orElseThrow();
        return new ProjectTask(
                projectTaskDTO.getProjectTaskId(),
                projectTaskDTO.getName(),
                projectTaskDTO.getCreatedAt(),
                projectTaskDTO.getDueDate(),
                status,
                employee,
                project
        );
    }
}

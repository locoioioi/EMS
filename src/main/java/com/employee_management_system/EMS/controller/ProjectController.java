package com.employee_management_system.EMS.controller;

import com.employee_management_system.EMS.dto.project.ProjectDTO;
import com.employee_management_system.EMS.dto.response.Response;
import com.employee_management_system.EMS.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/admin")
    public Page<ProjectDTO> getAll(@RequestParam() int page, @RequestParam int size) {
        return projectService.getAll(page,size);
    };

    @GetMapping("/employees/{employeeId}")
    public Page<ProjectDTO> getByEmployeeId(@PathVariable(name = "employeeId") int employeeId,@RequestParam() int page, @RequestParam int size) {
        return projectService.getAllByEmployeeId(employeeId,page,size);
    }

    @GetMapping("/departments/{departmentId}")
    public Page<ProjectDTO> getByDepartmentId(@PathVariable(name = "departmentId") int departmentId,@RequestParam int page,@RequestParam int size) {
        return projectService.getAllByDepartmentId(departmentId,page,size);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        if (projectService.addProject(projectDTO)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "Fail to create project",""));
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@RequestParam int employeeId,@RequestBody ProjectDTO projectDTO) {
        if (projectService.updateProject(employeeId,projectDTO)) {
            return ResponseEntity.ok(projectDTO);
        }
        return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "Fail to update project",""));
    }

    @PutMapping("/{projectId}/employees/{employeeId}/add")
    public ResponseEntity<?> addEmployeeToProject(
            @PathVariable(name = "projectId") int projectId,
            @RequestParam int projectManagerId,
            @PathVariable(name = "employeeId") int employeeId,
            @RequestParam String permission
    ) {
        try {
            return projectService.addEmployeeToProject(projectId, projectManagerId, employeeId, permission);
        } catch (IllegalAccessError error) {
            throw new RuntimeException("fail to add employee to project");
        }
    }

    @DeleteMapping("/{projectId}/employees/{employeeId}/remove")
    public ResponseEntity<?> removeEmployeeToProject(
            @PathVariable(name = "projectId") int projectId,
            @PathVariable(name = "employeeId") int employeeId,
            @RequestParam int projectManagerId
    ) {
        try {
            return projectService.removeEmployeeFromProject(projectId, projectManagerId, employeeId);
        } catch (IllegalAccessError error) {
            throw new RuntimeException("fail to add employee to project");
        }
    }
}

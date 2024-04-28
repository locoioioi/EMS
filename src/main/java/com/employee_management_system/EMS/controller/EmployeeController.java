package com.employee_management_system.EMS.controller;

import com.employee_management_system.EMS.dto.employee.EmployeeDTO;
import com.employee_management_system.EMS.exception.ExceptionResponse;
import com.employee_management_system.EMS.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(name = "employeeId") int employeeId) {
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam int page,@RequestParam int size) {
        Page<EmployeeDTO> employees = employeeService.getAll(page,size);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getAllByDepartmentId(
            @PathVariable(name = "departmentId") int departmentId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<EmployeeDTO> employees = employeeService.getAllByDepartmentId(departmentId,page,size);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getAllByProjectId(
        @PathVariable(name = "projectId") int projectId,
        @RequestParam int page,
        @RequestParam int size
    ) {
        Page<EmployeeDTO> employees = employeeService.getAllByProjectId(projectId, page, size);
        return ResponseEntity.ok(employees);
    }
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        boolean isAdded = employeeService.addEmployee(employeeDTO);
        if (isAdded) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body(new ExceptionResponse("fail to create employee", System.currentTimeMillis(),""));
    }
}

package com.employee_management_system.EMS.helper;

import com.employee_management_system.EMS.entity.Department;
import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.Project;
import com.employee_management_system.EMS.utils.ProjectStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
@Service
public class ObjectGenerator {
    public static Employee getEmployee() {
        return new Employee(
                1,"Loc","Truong",null,null,null,null,null,null,null);
    }

    public static Department getDepartment(Employee employee) {
        return new Department(
                1,
                "Landmark1",
                employee,
                new HashSet<>(),
                new HashSet<>()
        );
    }
    public static Project getProject(Employee employee, Department department) {
        return new Project(
                1,
                "E-commerce",
                ProjectStatus.INITIALIZE,
                LocalDateTime.now(),
                employee,
                new HashSet<>(),
                department,
                new HashSet<>(),
                new HashSet<>()
        );
    }
}

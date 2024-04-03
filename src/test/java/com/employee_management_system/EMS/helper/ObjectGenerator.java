package com.employee_management_system.EMS.helper;

import com.employee_management_system.EMS.entity.*;
import com.employee_management_system.EMS.utils.PermissionType;
import com.employee_management_system.EMS.utils.ProjectStatus;
import com.employee_management_system.EMS.utils.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;

@Service
public class ObjectGenerator {
    public static Role getRole() {
        return new Role(
                1,
                "Employee",
                new HashSet<>()
        );
    }
    public static ProjectPermission getProjectPermission(Employee employee,Project project) {
        return new ProjectPermission(
                1,
                PermissionType.Edit,
                employee,
                project
        );
    }
    public static User getUser() {
        return new User(
                1,
                "user1",
                "pass1",
                "user1@gmail.com",
                null,
                new HashSet<>()
        );
    }
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
    public static ProjectTask getProjectTask(Employee employee,Project project) {
        return new ProjectTask(
                1,
                "Read documentation",
                LocalDateTime.of(2024, 3,11,11,11),
                LocalDateTime.of(2024,4,15,11,11),
                TaskStatus.Working,
                employee,
                project
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

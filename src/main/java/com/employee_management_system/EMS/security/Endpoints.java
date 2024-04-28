package com.employee_management_system.EMS.security;

public class Endpoints {
    public static String[] POST_PUBLIC = {
            "/account/login",
            "/account/register"
    };

    public static String[] GET_ADMIN = {
            "/employees",
            "/employees/{employeeId}",
            "/employees/{departmentId}",
            "/employees/{projectId}",
            "/projects/admin",
            "/projects/departments/{departmentId}"
    };

    public static String[] POST_ADMIN = {
            "/employees",
            "/projects"
    };

    public static String[] GET_EMPLOYEE = {
            "/projects/employees/{employeeId}",
    };

    public static String[] PUT_EMPLOYEE = {
            "/projects",
            "/projects/{projectId}/employees"
    };
}

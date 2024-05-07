package com.employee_management_system.EMS.entity;

import com.employee_management_system.EMS.utils.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;

    @Column(name = "name")
    @NotNull(message = "Project name can not be null")
    private String name;

    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @OneToOne(
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "projects_employees",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ProjectPermission> permissions;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ProjectTask> tasks;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Objects.equals(name, project.name) && status == project.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        if (this.employee == employee) {
            this.employee = null;
        }
        employees.remove(employee);
    }

    public void addPermission(ProjectPermission projectPermission) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        permissions.add(projectPermission);
    }

    public void removePermissionByEmployee(Employee employee) {
        List<ProjectPermission> permissionList = permissions.stream().filter(
                projectPermission -> projectPermission.getEmployee().getId() == employee.getId()
        ).toList();

        permissions.removeAll(permissionList);
    }
}

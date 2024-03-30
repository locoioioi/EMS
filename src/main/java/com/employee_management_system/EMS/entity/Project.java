package com.employee_management_system.EMS.entity;

import com.employee_management_system.EMS.utils.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
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
    private Set<Employee> employees;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(
            mappedBy = "project",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private Set<ProjectPermission> permissions;

    @OneToMany(
            mappedBy = "project",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private Set<ProjectTask> tasks;
}

package com.employee_management_system.EMS.entity;

import com.employee_management_system.EMS.utils.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_task_id")
    private int id;

    @Column(name = "name")
    @NotNull(message = "Project task can not be empty")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "due_date")
    @Future(message = "Due date should be in the future")
    private LocalDateTime dueDate;

    @Column(name = "status")
    private TaskStatus status;

    @ManyToOne(
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Employee should be assign")
    private Employee employee;

    @ManyToOne(
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "project_id")
    @NotNull(message = "This task should be belong to a project")
    private Project project;
}

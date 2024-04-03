package com.employee_management_system.EMS.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotNull(message = "username can not be empty")
    @Size(min = 7,message = "minimum length of username is 7")
    private String username;

    @Column(name = "password")
    @Size(min = 8, message = "minimum length of password is 8")
    @NotNull(message = "password can not be empty")
    private String password;

    @Column(name = "email")
    @Email(message = "Require email type")
    @NotNull(message = "Email can not be null")
    private String email;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Employee employeeInformation;

    @ManyToMany(
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}

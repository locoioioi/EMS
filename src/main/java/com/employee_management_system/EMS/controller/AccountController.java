package com.employee_management_system.EMS.controller;

import com.employee_management_system.EMS.dto.response.LoginRequest;
import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.repository.ProjectRepository;
import com.employee_management_system.EMS.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            return accountService.login(loginRequest);
        } catch ( BadCredentialsException exception ) {
            throw new RuntimeException("login fail");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreationUser creationUser) {
        return accountService.register(creationUser);
    }
}

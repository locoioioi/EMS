package com.employee_management_system.EMS.service.account;

import com.employee_management_system.EMS.dto.response.LoginRequest;
import com.employee_management_system.EMS.dto.response.Token;
import com.employee_management_system.EMS.dto.user.CreationUser;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?> login(LoginRequest loginRequest);
    ResponseEntity<?> register(CreationUser request);
    ResponseEntity<?> refreshToken(String username,Token token);
}

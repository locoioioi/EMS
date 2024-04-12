package com.employee_management_system.EMS.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateAccessToken(String username);
    String generateRefreshToken(String username);
    boolean isValidateToken(UserDetails userDetails, String token);
}

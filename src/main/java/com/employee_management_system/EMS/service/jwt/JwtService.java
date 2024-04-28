package com.employee_management_system.EMS.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(String username);
    String generateRefreshToken(String username);
    boolean isValidateToken(UserDetails userDetails, String token);
    String extractSubject(String token);
    boolean isExpiredToken(String token);
}

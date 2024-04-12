package com.employee_management_system.EMS.service.account;

import com.employee_management_system.EMS.dto.response.LoginRequest;
import com.employee_management_system.EMS.dto.response.Token;
import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.dto.user.UserMapper;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.ExceptionResponse;
import com.employee_management_system.EMS.repository.UserRepository;
import com.employee_management_system.EMS.service.jwt.JwtService;
import com.employee_management_system.EMS.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            // ? create jwt
            String accessToken = jwtService.generateAccessToken(loginRequest.getUsername());
            String refreshToken = jwtService.generateRefreshToken(loginRequest.getUsername());
            Token token = new Token(accessToken,refreshToken);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body(new ExceptionResponse("login fail",System.currentTimeMillis(),""));
        }
    }

    @Override
    public ResponseEntity<?> register(CreationUser creationUser) {
        User user = userMapper.toUser(creationUser);
        bCryptPasswordEncoder.encode(user.getPassword());
        User saved = userRepository.saveAndFlush(user);
        return ResponseEntity.ok(userMapper.toDto(saved));
    }

    @Override
    public ResponseEntity<?> refreshToken(String username,Token token) {
        UserDetails user = userService.loadUserByUsername(username);
        boolean isValid = jwtService.isValidateToken(user,token.getRefreshToken());
        if (!isValid) {
            Token refreshToken = new Token(
                    jwtService.generateAccessToken(username),
                    jwtService.generateRefreshToken(username)
            );
            return ResponseEntity.ok(refreshToken);
        }
        return ResponseEntity.badRequest().body(new ExceptionResponse("refresh token has expired",System.currentTimeMillis(),"fail to refresh token"));
    }
}

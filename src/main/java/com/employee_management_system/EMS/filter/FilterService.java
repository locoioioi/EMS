package com.employee_management_system.EMS.filter;

import com.employee_management_system.EMS.dto.response.Response;
import com.employee_management_system.EMS.exception.JwtException;
import com.employee_management_system.EMS.service.jwt.JwtService;
import com.employee_management_system.EMS.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FilterService extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // * get the header from the request
            String header = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (header != null && header.startsWith("Bearer ")) {
                // * extract token
                token = header.substring(7);
                // * extract username from token
                username = jwtService.extractSubject(token);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // * load userDetails by username
                UserDetails userDetails = userService.loadUserByUsername(username);
                System.out.println(userDetails.getAuthorities());
                if (jwtService.isValidateToken(userDetails, token)) {
                    // * create an UsernamePasswordToken
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // * Merge the  UsernamePasswordToken  to the request and the context
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {
            ResponseEntity<Response> entity = new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Token has been expired", null), HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(entity.getBody()));
        }
    }


}

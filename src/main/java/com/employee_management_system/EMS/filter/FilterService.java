package com.employee_management_system.EMS.filter;

import com.employee_management_system.EMS.service.jwt.JwtService;
import com.employee_management_system.EMS.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
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
        // * get the header from the request
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (header != null && header.startsWith("Bearer ")) {
            // * extract token
            token = header.substring(7);
            // * extract username from token
            username = jwtService.getUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // * load userDetails by username
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtService.isValidateToken(userDetails,token)) {
                // * create an UsernamePasswordToken
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                // * Merge the  UsernamePasswordToken  to the request and the context
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}

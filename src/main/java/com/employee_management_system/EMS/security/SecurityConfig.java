package com.employee_management_system.EMS.security;

import com.employee_management_system.EMS.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider provider(UserService userService) {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userService);
        return dap;
    }
}

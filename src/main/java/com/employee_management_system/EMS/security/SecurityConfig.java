package com.employee_management_system.EMS.security;

import com.employee_management_system.EMS.filter.FilterService;
import com.employee_management_system.EMS.service.jwt.JwtService;
import com.employee_management_system.EMS.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final FilterService filterService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider provider(UserService userService) {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userService);
        dap.setPasswordEncoder(bCryptPasswordEncoder());
        return dap;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request ->
                        request
                                // * [POST]
                                .requestMatchers(HttpMethod.POST,Endpoints.POST_PUBLIC).permitAll()
                                .requestMatchers(HttpMethod.POST,Endpoints.POST_ADMIN).hasAuthority("ROLE_ADMIN")
                                // * [GET]
                                .requestMatchers(HttpMethod.GET,Endpoints.GET_ADMIN).hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET,Endpoints.GET_EMPLOYEE).hasAuthority("ROLE_EMPLOYEE")
                                // * [PUT]
                                .requestMatchers(HttpMethod.PUT,Endpoints.PUT_EMPLOYEE).hasAuthority("ROLE_EMPLOYEE")
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(
                cors -> cors
                        .configurationSource(
                                request -> {
                                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                                    corsConfiguration.addAllowedOrigin("*");
                                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                                    corsConfiguration.addAllowedHeader("*");
                                    return corsConfiguration;
                                }
                        )
                );
        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(filterService, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

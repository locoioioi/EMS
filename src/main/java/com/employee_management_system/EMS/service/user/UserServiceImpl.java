package com.employee_management_system.EMS.service.user;

import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("This use is not exist");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),getAuthorities(user));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }
}

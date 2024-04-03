package com.employee_management_system.EMS.service.user;

import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.dto.user.UserDTO;
import com.employee_management_system.EMS.dto.user.UserMapper;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
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

    @Override
    public UserDTO getUserById(int id) {
        User user = getUser(id);
        UserDTO userDTO = userMapper.toDto(user);
        System.out.println(userDTO);
        return userDTO;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDTO getUserByEmployeeId(int employeeId) {
        User user = userRepository.findByEmployeeInformation_Id(employeeId);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "employee id " + employeeId);
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO saveUser(CreationUser creationUser) {
        User user = userMapper.toUser(creationUser);
        User savedUser = userRepository.saveAndFlush(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDTO removeUser(int id) {
        User user = getUser(id);
        userRepository.delete(user);
        return userMapper.toDto(user);
    }

    private User getUser(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(User.class,"id " + id)
        );
    }
}

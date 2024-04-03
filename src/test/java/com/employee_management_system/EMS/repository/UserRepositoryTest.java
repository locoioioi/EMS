package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.entity.Employee;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.helper.ObjectGenerator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User user = new User(0,"Truong Loc", "123456123", "baoloctru@gmail.com",null,null);
        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenInvalidUsername_thenReturnsNull() {
        // given
        String username = "nonExistingUser";

        // when
        User found = userRepository.findByUsername(username);

        // then
        assertThat(found).isNull();
    }
}
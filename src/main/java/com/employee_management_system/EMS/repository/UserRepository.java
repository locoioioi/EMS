package com.employee_management_system.EMS.repository;

import com.employee_management_system.EMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u from User u where u.username = :username")
    User findByUsername(String username);

    @Query("SELECT u from User u where u.employeeInformation.id = :employeeId")
    User findByEmployeeInformation_Id(int employeeId);
}

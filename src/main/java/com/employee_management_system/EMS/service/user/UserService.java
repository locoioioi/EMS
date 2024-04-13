package com.employee_management_system.EMS.service.user;

import com.employee_management_system.EMS.dto.user.CreationUser;
import com.employee_management_system.EMS.dto.user.UserDTO;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * Retrieves a {@link UserDTO} by its id.
     * This method looks up a user by their id using the {@code userRepository}. If the user is found,
     * it is converted to {@link UserDTO} using {@code userMapper}. If no user is found with the given id,
     * an {@link EntityNotFoundException} is thrown.
     *
     * @param id the unique id of the user to retrieve
     * @return the {@link UserDTO} representation of the user
     * @throws EntityNotFoundException if no user is found with the provided id
     */
    UserDTO getUserById(int id);

    /**
     * Fetches all users and converts them to DTOs.
     * It retrieves every user from the database, then maps each user entity to a {@link UserDTO}.
     * This ensures the data is transferred in a simplified format.
     *
     * @return A list of {@link UserDTO}s, which could be empty if no users exist.
     */
    List<UserDTO> getUsers();

    /**
     * Retrieves a {@link UserDTO} associated with a given employee ID.
     * <p>
     * This method is responsible for finding a user based on the associated employee's ID.
     * It ensures that the user data is transferred in a simplified {@link UserDTO} format.
     *
     * @param employeeId the unique ID of the employee whose user information is to be retrieved.
     * @return the {@link UserDTO} corresponding to the given employee ID.
     * @throws EntityNotFoundException if no user is found for the specified employee ID.
     */
    UserDTO getUserByEmployeeId(int employeeId);
    /**
     * Saves a new user to the database.
     * Accepts user data in the form of a {@link CreationUser} DTO, converts it into a {@link User} entity,
     * and persists it in the database. After saving, the entity is converted back to a {@link UserDTO} to be returned,
     * reflecting the persisted state including any database-generated values (e.g., the user's ID).
     *
     * @param user the user data for creating a new user, encapsulated in a {@link CreationUser} DTO.
     * @return a {@link UserDTO} reflecting the newly created user's persisted state.
     */
    UserDTO saveUser(CreationUser user);

    UserDTO removeUser(int id);
}

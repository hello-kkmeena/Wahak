package com.wahak.service;

import com.wahak.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

/**
 * @author krishna.meena
 */

@Service
public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(@Valid UserDTO user);
}

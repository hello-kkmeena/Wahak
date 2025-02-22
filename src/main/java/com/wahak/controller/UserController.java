package com.wahak.controller;

import com.wahak.dto.UserDTO;
import com.wahak.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping("create")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("update")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }



}

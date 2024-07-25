package com.nobody.controller;

import com.nobody.model.User;
import com.nobody.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    @Operation(summary = "Get a list of users", description = "Return all users")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a User by Id", description = "Return user by id")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/role")
    @Operation(summary = "Get a User by Role", description = "Return a list of users")
    public ResponseEntity<Page<User>> getUsersByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUsersByRole(role, page, size);
    }

    @PostMapping("add")
    @Operation(summary = "Add User", description = "Create new user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Update information about user by their ID")
    public ResponseEntity<User> updateUserById(
            @PathVariable Integer id,
            @RequestBody User updatedUser
    ) {
        return userService.updateUserById(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Delete user by their ID")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

}

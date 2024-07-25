package com.nobody.service;

import com.nobody.enums.Role;
import com.nobody.model.User;
import com.nobody.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Page<User>> getAllUsers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<User> users = userRepository.findAll(pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> getUserById(Integer id) {
        try{
            Optional<User> user = userRepository.findById(id);
            return user.map(value
                    -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                    -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error fetching user by id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<User>> getUsersByRole(String role, int page, int size) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase());
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<User> users = userRepository.findByRole(userRole, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid role: {}", role, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error fetching users by role", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addUser(User user) {
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return new ResponseEntity<>("Error creating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<User> updateUserById(Integer id, User updatedUser) {
        try {
            return userRepository.findById(id)
                    .map(user -> {
                        user.setFirstname(updatedUser.getFirstname());
                        user.setLastname(updatedUser.getLastname());
                        user.setEmail(updatedUser.getEmail());
                        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                        }
                        user.setRole(updatedUser.getRole());
                        userRepository.save(user);
                        return new ResponseEntity<>(user, HttpStatus.OK);
                    })
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating user with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<String> deleteUserById(Integer id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting user with id: {}", id, e);
            return new ResponseEntity<>("Error deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

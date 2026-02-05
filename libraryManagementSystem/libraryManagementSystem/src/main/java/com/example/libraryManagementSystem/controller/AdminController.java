package com.example.libraryManagementSystem.controller;

import com.example.libraryManagementSystem.dto.ResponseWrapper;
import com.example.libraryManagementSystem.entity.User;
import com.example.libraryManagementSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ResponseWrapper<List<User>>> getUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Users retrieved", users));
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseWrapper<User>> createUser(@RequestBody User user) {
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        if (user.getEnabled() == null) {
            user.setEnabled(true);
        }
        User saved = userService.save(user);
        log.info("✓ New user created: {}", user.getUsername());
        return ResponseEntity.ok(new ResponseWrapper<>(true, "User created", saved));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existing = userService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        User updated = userService.save(user);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "User updated", updated));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        log.info("✓ User deleted: {}", id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "User deleted", null));
    }
}

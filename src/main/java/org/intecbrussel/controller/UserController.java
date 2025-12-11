package org.intecbrussel.controller;

import org.intecbrussel.dto.UserRegisterRequest;
import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.dto.UserUpdateRequest;
import org.intecbrussel.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User endpoints (register / profile / update / delete).
 * NOTE: login & JWT token endpoints are implemented in AuthController (stap 2).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // register
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    // get profile (should be protected so only owner can read — add security later)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    // update profile (only owner; security to add later)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // delete profile (only owner; security to add later)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User verwijderd");
    }
}

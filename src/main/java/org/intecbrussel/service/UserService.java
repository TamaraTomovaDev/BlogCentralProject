package org.intecbrussel.service;

import org.intecbrussel.dto.UserRegisterRequest;
import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.dto.UserUpdateRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    UserResponse getById(Long id);
    UserResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
    // login/jwt wordt later toegevoegd (AuthService)
}

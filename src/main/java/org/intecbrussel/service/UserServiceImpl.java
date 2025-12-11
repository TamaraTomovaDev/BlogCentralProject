package org.intecbrussel.service.impl;

import org.intecbrussel.dto.UserRegisterRequest;
import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.dto.UserUpdateRequest;
import org.intecbrussel.mapper.UserMapper;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.UserRepository;
import org.intecbrussel.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(UserRegisterRequest request) {
        // basic validation
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is verplicht");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username bestaat al");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email bestaat al");
        }
        if (!request.getPassword().equals(request.getRetypePassword())) {
            throw new IllegalArgumentException("Wachtwoorden komen niet overeen");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // standaard rol

        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User niet gevonden"));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User niet gevonden"));

        // username mag niet aangepast worden volgens opdracht
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setStreet(request.getStreet());
        user.setHouseNr(request.getHouseNr());
        user.setCity(request.getCity());
        user.setZip(request.getZip());

        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User niet gevonden");
        }
        userRepository.deleteById(id);
    }
}

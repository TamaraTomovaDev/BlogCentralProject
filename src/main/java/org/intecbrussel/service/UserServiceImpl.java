package org.intecbrussel.service;

import org.intecbrussel.dto.UserRegisterRequest;
import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.dto.UserUpdateRequest;
import org.intecbrussel.exception.BadRequestException;
import org.intecbrussel.exception.ResourceNotFoundException;
import org.intecbrussel.exception.UserNameAlreadyExistsException;
import org.intecbrussel.mapper.UserMapper;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.UserRepository;
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

    // -------------------------
    // REGISTRATIE
    // -------------------------
    @Override
    public UserResponse register(UserRegisterRequest request) {

        // Validaties
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new BadRequestException("Gebruikersnaam is verplicht.");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserNameAlreadyExistsException("Gebruikersnaam bestaat al.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email bestaat al.");
        }

        if (!request.getPassword().equals(request.getRetypePassword())) {
            throw new BadRequestException("Wachtwoorden komen niet overeen.");
        }

        // User maken
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

    // -------------------------
    // GET USER BY ID
    // -------------------------
    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User met id " + id + " niet gevonden."));

        return UserMapper.toResponse(user);
    }

    // -------------------------
    // UPDATE PROFIEL
    // -------------------------
    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User met id " + id + " niet gevonden."));

        // username mag NIET gewijzigd worden volgens de opdracht
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

    // -------------------------
    // DELETE USER
    // -------------------------
    @Override
    public void delete(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User met id " + id + " niet gevonden.");
        }

        userRepository.deleteById(id);
    }
}

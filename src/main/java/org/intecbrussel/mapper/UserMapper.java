package org.intecbrussel.mapper;

import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) return null;

        UserResponse r = new UserResponse();
        r.setId(user.getId());
        r.setFirstName(user.getFirstName());
        r.setLastName(user.getLastName());
        r.setUsername(user.getUsername());
        r.setEmail(user.getEmail());
        r.setAvatarUrl(user.getAvatarUrl());
        r.setStreet(user.getStreet());
        r.setHouseNr(user.getHouseNr());
        r.setCity(user.getCity());
        r.setZip(user.getZip());
        if (user.getRole() != null) {
            r.setRole(user.getRole().name());
        }
        return r;
    }
}

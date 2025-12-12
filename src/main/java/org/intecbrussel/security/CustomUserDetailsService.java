package org.intecbrussel.security;
import org.intecbrussel.exception.ResourceNotFoundException;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private
    UserRepository userRepository;

    // this method is called when user logs in OR the authentication filter validates token
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username)
                );
        return new CustomUserDetails(user);
    }
}

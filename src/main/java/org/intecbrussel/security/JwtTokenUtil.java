package org.intecbrussel.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    // Load secret + expiration from application.properties
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration:3600000}") //fallback of 1 hour
    private long EXPIRATION;

    @Value("${jwt.expirationRememberMe:604800000}")  // fallback of 7 days
    private long EXPIRATION_REMEMBER_ME;

    // secret is converted into crypted key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // generate JWT token with username and role in subject
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRememberMeToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_REMEMBER_ME))  // Uses EXPIRATION_REMEMBER_ME value from properties
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // extract username from JWT token
    public String extractUsername(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    // extract role from JWT token
    public String extractRole(String token) {
        return parseClaims(token).getBody().get("role").toString();
    }

    // validate token (correct, signed, correct role and not expired)
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // parse and validate tokens
    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    // verify the role
    public boolean hasRole(String token, String role) {
        String userRole = extractRole(token);
        return userRole != null && userRole.equals(role);
    }
}



/*
JwtTokenUtil – Generates & validates JWT tokens
JwtAuthenticationFilter – Extracts JWT from requests
CustomUserDetails – Wraps your User entity
CustomUserDetailsService – Loads user from DB for login/auth
JwtAuthenticationEntryPoint – Handles 401 (not logged in)
JwtAccessDeniedHandler – Handles 403 (no permission)
SecurityConfig – The central Spring Security configuration
*/
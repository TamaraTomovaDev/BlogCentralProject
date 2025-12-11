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

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    // secret is converted into crypted key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // generate JWT token with username in subject
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // extract username from JWT token
    public String extractUsername(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    // validate token (correct, signed and not expired)
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
        return Jwts.builder()
                .setSigningKey(getSigningKey())   // This works now
                .build()
                .parseClaimsJws(token)
                .getBody();
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
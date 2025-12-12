package org.intecbrussel.config;

import org.intecbrussel.security.JWTAccessDeniedHandler;
import org.intecbrussel.security.JWTAuthenticationEntryPoint;
import org.intecbrussel.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;

    // BCrypt to hash passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //returns authentication manager, used for Login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // main security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //deals with 401
                                .accessDeniedHandler(jwtAccessDeniedHandler) // deals with 403
                )
                .sessionManagement(sessionManagement -> sessionManagement
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizedRequest -> authorizedRequest
                    .requestMatchers("/users/login", "/users/register").permitAll() // public endpoints
                    .requestMatchers("/search","/blogposts", "/blogposts/{postId}").permitAll() //  blogPosts can be viewed by anyone
                    .requestMatchers("/blogposts", "/blogposts/{postId}/like", "/comments", "/comments/{commentId}").authenticated() // requires login
                    .requestMatchers("/admin/**").hasRole("ADMIN") // only access by admin
                    .anyRequest().authenticated() // endpoints with required login
                )
                // disable default login mechanisms (from the short config)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                // add custom JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


/* In Spring Security, CSRF protection is enabled by default.
This means that any state-modifying HTTP request (POST, PUT, DELETE) must include a CSRF token
to ensure that the request is coming from a trusted source (usually from the same
application). However, for a stateless REST API that uses JWT for authentication,
CSRF protection is not necessary, as JWTs are not vulnerable to CSRF attacks.
*/
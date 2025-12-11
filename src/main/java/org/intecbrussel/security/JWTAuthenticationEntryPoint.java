package org.intecbrussel.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.intecbrussel.exception.APIError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException ex
    ) throws IOException {

        APIError error = new APIError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Authentication required",
                req.getRequestURI()
        );

        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType("application/json");
        new ObjectMapper().writeValue(res.getOutputStream(), error);
    }
}

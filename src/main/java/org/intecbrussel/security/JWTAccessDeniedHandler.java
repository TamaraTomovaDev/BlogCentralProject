package org.intecbrussel.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.intecbrussel.exception.APIError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;

// when user is authenticated but not allowed to do an action
@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest req,
            HttpServletResponse res,
            AccessDeniedException ex
    ) throws IOException {

        APIError error = new APIError(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "You do not have permission to perform this action",
                req.getRequestURI()
        );

        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.setContentType("application/json");
        new ObjectMapper().writeValue(res.getOutputStream(), error);
    }
}

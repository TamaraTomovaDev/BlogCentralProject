package org.intecbrussel.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.NOT_FOUND.value(),
                "not found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<APIError> handleUserNameAlreadyExists(UserNameAlreadyExistsException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.CONFLICT.value(),
                "conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIError> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.UNAUTHORIZED.value(),
                "unauthorized",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIError> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.UNAUTHORIZED.value(),
                "unauthorized",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<APIError> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.FORBIDDEN.value(),
                "forbidden",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIError> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.BAD_REQUEST.value(),
                "bad request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.BAD_REQUEST.value(),
                "validation error",
                "validation failed",
                request.getRequestURI()
        );

        error.setValidationErrors(
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(field -> field.getField() + ": " + field.getDefaultMessage())
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleExceptions(Exception ex, HttpServletRequest request) {
        APIError error = new APIError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "internal server error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

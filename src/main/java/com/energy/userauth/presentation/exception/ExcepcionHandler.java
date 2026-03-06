package com.energy.userauth.presentation.exception;

import com.energy.userauth.domain.exception.IdentityLinkAlreadyExistsException;
import com.energy.userauth.domain.exception.InvalidEmailException;
import com.energy.userauth.domain.exception.InvalidUserNameException;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.exception.UserDomainException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@ControllerAdvice
@SuppressWarnings("unused")
public class ExcepcionHandler {

    @ExceptionHandler(InvalidUserNameException.class)
    public ResponseEntity<ApiError> handleInvalidUserNameException(InvalidUserNameException ex,
                                                                   HttpServletRequest request) {
        return response(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiError> handleInvalidEmailException(InvalidEmailException ex,
                                                                HttpServletRequest request) {
        return response(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, IdentityLinkAlreadyExistsException.class})
    public ResponseEntity<ApiError> handleConflict(UserDomainException ex, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                   HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, "Resource not found.", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                          HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, "Resource conflicts with existing data constraints.", request);
    }

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<ApiError> handleUserDomainException(UserDomainException ex, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected internal error.", request);
    }

    private ResponseEntity<ApiError> response(HttpStatus status, String message, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(apiError);
    }
}

package com.energy.userauth.presentation.exception;

import com.energy.userauth.domain.exception.InvalidEmailException;
import com.energy.userauth.domain.exception.InvalidUserNameException;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.exception.UserDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcepcionHandler {

    @ExceptionHandler(InvalidUserNameException.class)
    public ResponseEntity<String> handleInvalidUserNameException(InvalidUserNameException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<String> handleUserDomainException(UserDomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}


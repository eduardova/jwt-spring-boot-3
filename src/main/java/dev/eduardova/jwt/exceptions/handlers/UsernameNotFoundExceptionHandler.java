package dev.eduardova.jwt.exceptions.handlers;

import dev.eduardova.jwt.dtos.utils.SimpleMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsernameNotFoundExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleMessage handler(final UsernameNotFoundException ex) {
        return new SimpleMessage(ex.getMessage());
    }

}

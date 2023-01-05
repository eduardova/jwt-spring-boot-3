package dev.eduardova.jwt.exceptions.handlers;

import dev.eduardova.jwt.dtos.utils.SimpleMessage;
import dev.eduardova.jwt.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleMessage handler(final NotFoundException ex) {
        return new SimpleMessage(ex.getMessage());
    }

}

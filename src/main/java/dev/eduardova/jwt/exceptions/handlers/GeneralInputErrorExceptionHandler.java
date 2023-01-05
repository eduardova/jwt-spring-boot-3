package dev.eduardova.jwt.exceptions.handlers;

import dev.eduardova.jwt.dtos.utils.SimpleMessage;
import dev.eduardova.jwt.exceptions.GeneralInputErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralInputErrorExceptionHandler {

    @ExceptionHandler(GeneralInputErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleMessage handler(final GeneralInputErrorException ex) {
        return new SimpleMessage(ex.getMessage());
    }

}

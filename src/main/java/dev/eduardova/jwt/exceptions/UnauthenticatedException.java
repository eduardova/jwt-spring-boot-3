package dev.eduardova.jwt.exceptions;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException(String msg) {
        super(msg);
    }

}

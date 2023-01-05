package dev.eduardova.jwt.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {
        this("The user already exist");
    }

    public UserAlreadyExistException(String msg) {
        super(msg);
    }

}

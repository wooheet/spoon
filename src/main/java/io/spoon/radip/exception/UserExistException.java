package io.spoon.radip.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserExistException(String msg) {
        super(msg);
    }

    public UserExistException() {
        super();
    }
}

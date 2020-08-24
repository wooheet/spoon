package io.spoon.radip.exception;

public class FanExistException extends RuntimeException {
    public FanExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public FanExistException(String msg) {
        super(msg);
    }

    public FanExistException() {
        super();
    }
}

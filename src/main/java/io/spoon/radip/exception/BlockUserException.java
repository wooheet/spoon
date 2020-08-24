package io.spoon.radip.exception;

public class BlockUserException extends RuntimeException {
    public BlockUserException(String msg, Throwable t) {
        super(msg, t);
    }

    public BlockUserException(String msg) {
        super(msg);
    }

    public BlockUserException() {
        super();
    }
}

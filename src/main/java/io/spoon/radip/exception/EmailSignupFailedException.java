package io.spoon.radip.exception;

public class EmailSignupFailedException extends RuntimeException {
    public EmailSignupFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailSignupFailedException(String msg) {
        super(msg);
    }

    public EmailSignupFailedException() {
        super();
    }
}

package com.github.lens.processor;

/**
 * Exception to represent lens processing errors.
 *
 * @author Sergei_Khadanovich
 */
public class LensProcessingException extends RuntimeException {

    private final Message error;

    public LensProcessingException(Message error) {
        super(error.msg);
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}

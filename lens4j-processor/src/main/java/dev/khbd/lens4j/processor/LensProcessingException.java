package dev.khbd.lens4j.processor;

import lombok.Getter;

/**
 * Exception to represent lens processing errors.
 *
 * @author Sergei_Khadanovich
 */
@Getter
public class LensProcessingException extends RuntimeException {

    private final Message error;

    public LensProcessingException(Message error) {
        super(error.getMsg());
        this.error = error;
    }
}

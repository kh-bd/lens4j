package com.github.lens.processor;

import java.util.List;

/**
 * Value with messages pair.
 *
 * @author Sergei_Khadanovich
 */
public class Result<M> {

    M value;
    List<Message> messages;

    private Result(M value, List<Message> messages) {
        this.value = value;
        this.messages = messages;
    }

    /**
     * Create result from value with any message.
     *
     * @param value    value
     * @param messages messages
     * @param <M>      value type
     * @return result
     */
    public static <M> Result<M> of(M value, Message... messages) {
        return new Result<>(value, List.of(messages));
    }

    /**
     * Create result from messages without any value.
     *
     * @param messages messages
     * @param <M>      value type
     * @return result
     */
    public static <M> Result<M> of(Message... messages) {
        return new Result<>(null, List.of(messages));
    }

}

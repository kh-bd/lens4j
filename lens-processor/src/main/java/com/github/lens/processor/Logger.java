package com.github.lens.processor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * {@link Messager} wrapper to log messages more conviniently.
 *
 * @author Sergei_Khadanovich
 */
public class Logger {

    private final Messager messager;

    public Logger(Messager messager) {
        this.messager = messager;
    }

    /**
     * Log message.
     *
     * @param message message
     */
    public void log(Message message) {
        Element element = message.getElement();
        if (Objects.nonNull(element)) {
            messager.printMessage(message.getKind(), message.getMsg(), element);
        } else {
            messager.printMessage(message.getKind(), message.getMsg());
        }
    }

    /**
     * Log all messages.
     *
     * @param messages messages
     */
    public void log(Message... messages) {
        log(List.of(messages));
    }

    /**
     * Log all messages.
     *
     * @param messages all messages
     */
    public void log(Collection<Message> messages) {
        for (Message message : messages) {
            log(message);
        }
    }
}

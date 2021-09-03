package com.github.lens.processor;

import lombok.Value;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Value to represent some messages which can occur during annotation processing.
 *
 * @author Sergei_Khadanovich
 */
@Value(staticConstructor = "of")
public class Message {

    Diagnostic.Kind kind;
    String msg;
    Element element;

    /**
     * Create error message.
     *
     * @param msg     message text
     * @param element the element to use as a position hint
     * @return message
     */
    public static Message error(String msg, Element element) {
        return of(Diagnostic.Kind.ERROR, msg, element);
    }

    /**
     * Create error message.
     *
     * @param msg message text
     * @return message
     */
    public static Message error(String msg) {
        return error(msg, null);
    }

    /**
     * Create warn message.
     *
     * @param msg     message text
     * @param element the element to use as a position hint
     * @return message
     */
    public static Message warn(String msg, Element element) {
        return of(Diagnostic.Kind.WARNING, msg, element);
    }

    /**
     * Create warn message.
     *
     * @param msg message text
     * @return message
     */
    public static Message warn(String msg) {
        return warn(msg, null);
    }
}

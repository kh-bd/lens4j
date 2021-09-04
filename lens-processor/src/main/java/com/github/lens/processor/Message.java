package com.github.lens.processor;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Value to represent some messages which can occur during annotation processing.
 *
 * @author Sergei_Khadanovich
 */
public class Message {

    Diagnostic.Kind kind;
    String msg;
    Element element;

    private Message(Diagnostic.Kind kind, String msg, Element element) {
        this.kind = kind;
        this.msg = msg;
        this.element = element;
    }

    /**
     * Create error message.
     *
     * @param msg     message text
     * @param element the element to use as a position hint
     * @return message
     */
    public static Message error(String msg, Element element) {
        return new Message(Diagnostic.Kind.ERROR, msg, element);
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
        return new Message(Diagnostic.Kind.WARNING, msg, element);
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

    /**
     * Get kind.
     *
     * @return kind
     */
    public Diagnostic.Kind getKind() {
        return kind;
    }

    /**
     * Get message.
     *
     * @return message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Get element.
     *
     * @return element
     */
    public Element getElement() {
        return element;
    }
}

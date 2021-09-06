package dev.khbd.lens4j.processor;

import javax.lang.model.element.Element;

/**
 * Value to represent some messages which can occur during annotation processing.
 *
 * @author Sergei_Khadanovich
 */
public class Message {

    String msg;
    Element element;

    private Message(String msg, Element element) {
        this.msg = msg;
        this.element = element;
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

    /**
     * Create message.
     *
     * @param msg     message text
     * @param element the element to use as a position hint
     * @return message
     */
    public static Message of(String msg, Element element) {
        return new Message(msg, element);
    }

    /**
     * Create message.
     *
     * @param msg message text
     * @return message
     */
    public static Message of(String msg) {
        return new Message(msg, null);
    }
}

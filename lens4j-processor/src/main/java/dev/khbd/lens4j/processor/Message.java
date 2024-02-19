package dev.khbd.lens4j.processor;

import lombok.Value;

import javax.lang.model.element.Element;

/**
 * Value to represent some messages which can occur during annotation processing.
 *
 * @author Sergei_Khadanovich
 */
@Value(staticConstructor = "of")
public class Message {

    String msg;
    Element element;

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

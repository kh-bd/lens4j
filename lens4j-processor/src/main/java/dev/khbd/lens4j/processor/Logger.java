package dev.khbd.lens4j.processor;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.Collection;

/**
 * {@link Messager} wrapper to log messages more conveniently.
 *
 * @author Sergei_Khadanovich
 */
public class Logger {

    private final Messager messager;

    public Logger(Messager messager) {
        this.messager = messager;
    }

    /**
     * Log all messages as errors.
     *
     * @param messages messages
     */
    public void error(Message... messages) {
        error(Arrays.asList(messages));
    }

    /**
     * Log all messages as errors.
     *
     * @param messages all messages
     */
    public void error(Collection<Message> messages) {
        log(Diagnostic.Kind.ERROR, messages);
    }

    /**
     * Log all messages as warnings.
     *
     * @param messages messages
     */
    public void warn(Message... messages) {
        warn(Arrays.asList(messages));
    }

    /**
     * Log all messages as warnings.
     *
     * @param messages all messages
     */
    public void warn(Collection<Message> messages) {
        log(Diagnostic.Kind.WARNING, messages);
    }

    private void log(Diagnostic.Kind kind, Collection<Message> messages) {
        for (Message message : messages) {
            messager.printMessage(kind, message.getMsg(), message.getElement());
        }
    }
}

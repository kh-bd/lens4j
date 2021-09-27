package dev.khbd.lens4j.processor;

import javax.lang.model.element.Element;

/**
 * @author Sergei_Khadanovich
 */
public final class MessageFactory {

    private MessageFactory() {
    }

    public static Message fieldNotFound(Element classElement, String fieldName) {
        String msg = String.format("Field '%s' was not found in class '%s'", fieldName, classElement.getSimpleName());
        return Message.of(msg, classElement);
    }

    public static Message wrongPlaceOfPrimitiveType(Element classElement) {
        return Message.of("Primitive types are allowed only as last part of a path", classElement);
    }

    public static Message genLensNotAllowedHere(Element element) {
        return Message.of("@GenLenses is not allowed here", element);
    }

    public static Message genLensNotAllowedOnInnerClasses(Element element) {
        return Message.of("@GenLenses is not allowed on inner classes", element);
    }

    public static Message genLensNotAllowedOnGenericClasses(Element element) {
        return Message.of("@GenLenses is not allowed on generic classes", element);
    }

    public static Message existNotUniqueLensName(Element element) {
        return Message.of("Lens names for type should be unique", element);
    }

    public static Message pathIsEmpty(Element element) {
        return Message.of("Lens path should be not empty", element);
    }
}

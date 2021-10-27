package dev.khbd.lens4j.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVariable;

/**
 * @author Sergei_Khadanovich
 */
public final class MessageFactory {

    private MessageFactory() {
    }

    public static Message unsupportedTypeKind(TypeKind typeKind) {
        return Message.of("Unsupported type kind: " + typeKind);
    }

    public static Message actualTypeParameterNotFound(DeclaredType classType, int index) {
        String msg = String.format("Type %s does not have type parameter at index %d", classType.toString(), index);
        return Message.of(msg, classType.asElement());
    }

    public static Message formalTypeParameterWasNotFound(TypeElement classElement, TypeVariable typeVar) {
        String msg = String.format("Type variable %s was not found in class %s", typeVar, classElement.getSimpleName());
        return Message.of(msg, classElement);
    }

    public static Message fieldNotFound(Element classElement, String fieldName) {
        String msg = String.format("Field '%s' was not found in class '%s'", fieldName, classElement.getSimpleName());
        return Message.of(msg, classElement);
    }

    public static Message methodNotFound(Element classElement, String methodName) {
        String msg = String.format("Method '%s' was not found in class '%s'", methodName, classElement.getSimpleName());
        return Message.of(msg, classElement);
    }

    public static Message methodAtWrongPosition(Element classElement) {
        String msg = "Methods are not allowed at last position of read-write lenses";
        return Message.of(msg, classElement);
    }

    public static Message nonDeclaredTypeFound(Element classElement) {
        return Message.of("Non-declared types are allowed only at last position in path", classElement);
    }

    public static Message genLensNotAllowedHere(Element element) {
        return Message.of("@GenLenses is not allowed here", element);
    }

    public static Message genLensNotAllowedOnGenericClasses(Element element) {
        return Message.of("@GenLenses is not allowed on generic classes", element);
    }

    public static Message existNotUniqueLensName(Element element) {
        return Message.of("Lens names for type should be unique", element);
    }

    public static Message pathIsIncorrect(Element element) {
        return Message.of("Lens path is incorrect", element);
    }
}

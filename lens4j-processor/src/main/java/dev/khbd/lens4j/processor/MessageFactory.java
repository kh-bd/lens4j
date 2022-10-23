package dev.khbd.lens4j.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVariable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Sergei_Khadanovich
 */
public final class MessageFactory {

    private static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle("messages.message");

    private MessageFactory() {
    }

    public static Message parametrizedMethodIsNotAllowed(Element element) {
        return Message.of(makeMessage("parametrized_method_is_not_allowed"), element);
    }

    public static Message unsupportedTypeKind(TypeKind typeKind) {
        return Message.of(makeMessage("unsupported_type_kind", typeKind));
    }

    public static Message actualTypeParameterNotFound(DeclaredType classType, int index) {
        return Message.of(
                makeMessage("actual_type_parameter_not_found", classType.toString(), index),
                classType.asElement()
        );
    }

    public static Message formalTypeParameterWasNotFound(TypeElement classElement, TypeVariable typeVar) {
        return Message.of(
                makeMessage("formal_type_parameter_was_not_found", typeVar, classElement.getSimpleName()),
                classElement
        );
    }

    public static Message fieldNotFound(Element classElement, String fieldName) {
        return Message.of(
                makeMessage("field_not_found", fieldName, classElement.getSimpleName()),
                classElement
        );
    }

    public static Message methodNotFound(Element classElement, String methodName) {
        return Message.of(
                makeMessage("method_not_found", methodName, classElement.getSimpleName()),
                classElement
        );
    }

    public static Message methodAtWrongPosition(Element classElement) {
        return Message.of(makeMessage("method_at_wrong_position"), classElement);
    }

    public static Message recordPropertyAtWrongPosition(Element classElement) {
        return Message.of(makeMessage("record_property_at_wrong_position"), classElement);
    }

    public static Message nonDeclaredTypeFound(Element classElement) {
        return Message.of(makeMessage("non_declared_type_found"), classElement);
    }

    public static Message genLensNotAllowedHere(Element element) {
        return Message.of(makeMessage("gen_lens_not_allowed_here"), element);
    }

    public static Message genLensNotAllowedOnGenericClasses(Element element) {
        return Message.of(makeMessage("gen_lens_not_allowed_on_generic_classes"), element);
    }

    public static Message existNotUniqueLensName(Element element) {
        return Message.of(makeMessage("exist_not_unique_lens_name"), element);
    }

    public static Message pathIsIncorrect(Element element) {
        return Message.of(makeMessage("path_is_incorrect"), element);
    }


    public static Message arraysPropertyIsNotSupported(String fieldName) {
        return Message.of(makeMessage("arrays_property_is_not_supported", fieldName));
    }

    private static String makeMessage(String key, Object... params) {
        String pattern = MESSAGE_BUNDLE.getString(key);
        return MessageFormat.format(pattern, params);
    }
}

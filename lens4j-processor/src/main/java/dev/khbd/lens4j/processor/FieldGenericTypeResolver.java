package dev.khbd.lens4j.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import java.util.List;

/**
 * @author Sergei_Khadanovich
 */
public class FieldGenericTypeResolver {

    private final LinerHierarchy<TypeElement> hierarchy;

    public FieldGenericTypeResolver(TypeElement rootClass) {
        this.hierarchy = ProcessorUtils.getInheritanceHierarchy(rootClass);
    }

    /**
     * Resolve actual type of type variable.
     *
     * @param fieldClass field containing class
     * @param fieldType  field type variable
     * @return resolved type
     */
    public TypeMirror resolveGenericType(TypeElement fieldClass,
                                         TypeVariable fieldType) {
        int index = findFormalParameterIndex(fieldClass, fieldType);
        return resolveGenericTypeByIndex(hierarchy, fieldClass, index);
    }

    private TypeMirror resolveGenericTypeByIndex(LinerHierarchy<TypeElement> hierarchy,
                                                 TypeElement fieldClass,
                                                 int index) {
        TypeElement child = hierarchy.findFirstUnder(fieldClass)
                .orElseThrow(() -> new LensProcessingException(MessageFactory.noSubClassInHierarchy(fieldClass)));

        DeclaredType childSuperClassType = (DeclaredType) child.getSuperclass();

        TypeMirror actualParameterType = findActualParameterByIndex(childSuperClassType, index);
        if (actualParameterType.getKind() == TypeKind.TYPEVAR) {
            int childIndex = findFormalParameterIndex(child, (TypeVariable) actualParameterType);
            return resolveGenericTypeByIndex(hierarchy, child, childIndex);
        }

        return actualParameterType;
    }

    private int findFormalParameterIndex(TypeElement classElement,
                                         TypeVariable type) {
        List<? extends TypeParameterElement> parameters = classElement.getTypeParameters();
        for (int i = 0; i < parameters.size(); i++) {
            TypeParameterElement parameter = parameters.get(i);
            if (matches(parameter, type)) {
                return i;
            }
        }
        throw new LensProcessingException(MessageFactory.formalTypeParameterWasNotFound(classElement, type));
    }

    private boolean matches(TypeParameterElement parameter, TypeVariable type) {
        return parameter.toString().equals(type.toString());
    }

    private TypeMirror findActualParameterByIndex(DeclaredType classType,
                                                  int index) {
        List<? extends TypeMirror> actualParameters = classType.getTypeArguments();
        if (actualParameters.size() <= index) {
            throw new LensProcessingException(MessageFactory.actualTypeParameterNotFound(classType, index));
        }
        return actualParameters.get(index);
    }

}

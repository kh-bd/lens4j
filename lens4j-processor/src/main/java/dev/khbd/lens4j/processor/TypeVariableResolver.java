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
public class TypeVariableResolver {

    private final LinerHierarchy<TypeElement> hierarchy;

    public TypeVariableResolver(TypeElement rootClass) {
        this.hierarchy = ProcessorUtils.getInheritanceHierarchy(rootClass);
    }

    /**
     * Resolve actual type of type variable.
     *
     * @param typeVarClass class containing type variable
     * @param typeVar      type variable
     * @return resolved type
     */
    public TypeMirror resolveType(TypeElement typeVarClass,
                                  TypeVariable typeVar) {
        int index = findFormalParameterIndex(typeVarClass, typeVar);
        return resolveTypeVariableByIndex(hierarchy, typeVarClass, index);
    }

    private TypeMirror resolveTypeVariableByIndex(LinerHierarchy<TypeElement> hierarchy,
                                                  TypeElement typeVarClass,
                                                  int index) {
        TypeElement child = hierarchy.findFirstUnder(typeVarClass)
                .orElseThrow(() -> new LensProcessingException(MessageFactory.noSubClassInHierarchy(typeVarClass)));

        DeclaredType childSuperClassType = (DeclaredType) child.getSuperclass();

        TypeMirror actualParameterType = findActualParameterByIndex(childSuperClassType, index);
        if (actualParameterType.getKind() == TypeKind.TYPEVAR) {
            int childIndex = findFormalParameterIndex(child, (TypeVariable) actualParameterType);
            return resolveTypeVariableByIndex(hierarchy, child, childIndex);
        }

        return actualParameterType;
    }

    private int findFormalParameterIndex(TypeElement typeVarClass,
                                         TypeVariable typeVar) {
        List<? extends TypeParameterElement> parameters = typeVarClass.getTypeParameters();
        for (int i = 0; i < parameters.size(); i++) {
            TypeParameterElement parameter = parameters.get(i);
            if (matches(parameter, typeVar)) {
                return i;
            }
        }
        throw new LensProcessingException(MessageFactory.formalTypeParameterWasNotFound(typeVarClass, typeVar));
    }

    private boolean matches(TypeParameterElement parameter, TypeVariable typeVariable) {
        return parameter.toString().equals(typeVariable.toString());
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

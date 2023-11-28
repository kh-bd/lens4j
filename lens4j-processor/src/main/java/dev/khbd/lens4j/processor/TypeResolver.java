package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import java.util.List;

/**
 * Parametrized type mirror resolver.
 *
 * @author Sergei_Khadanovich
 */
public class TypeResolver {

    private final LinerHierarchy<TypeElement> hierarchy;
    private final List<ResolvedParametrizedTypeMirror> actualTypeArguments;

    public TypeResolver(TypeElement rootClass, List<ResolvedParametrizedTypeMirror> actualTypeArguments) {
        this.hierarchy = ProcessorUtils.getInheritanceHierarchy(rootClass);
        this.actualTypeArguments = actualTypeArguments;
    }

    /**
     * Resolve type.
     *
     * @param currentTypeElement enclosing element
     * @param type               type
     * @return resolved parametrized type
     */
    public ResolvedParametrizedTypeMirror resolveType(TypeElement currentTypeElement,
                                                      TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return new ResolvedParametrizedTypeMirror(type);
        } else if (type.getKind() == TypeKind.ARRAY) {
            return resolveArrayType(currentTypeElement, (ArrayType) type);
        } else if (type.getKind() == TypeKind.TYPEVAR) {
            return resolveTypeVariable(currentTypeElement, (TypeVariable) type);
        } else if (type.getKind() == TypeKind.DECLARED) {
            return resolveDeclaredType(currentTypeElement, (DeclaredType) type);
        }
        throw new LensProcessingException(MessageFactory.unsupportedTypeKind(type.getKind()));
    }

    private ResolvedParametrizedTypeMirror resolveDeclaredType(TypeElement currentTypeElement,
                                                               DeclaredType type) {
        ResolvedParametrizedTypeMirror result = new ResolvedParametrizedTypeMirror(type);
        for (TypeMirror typeArgument : type.getTypeArguments()) {
            result.withActualTypeArgument(resolveType(currentTypeElement, typeArgument));
        }
        return result;
    }

    private ResolvedParametrizedTypeMirror resolveArrayType(TypeElement currentTypeElement,
                                                            ArrayType arrayType) {
        return new ResolvedParametrizedTypeMirror(arrayType)
                .withActualTypeArgument(resolveType(currentTypeElement, arrayType.getComponentType()));
    }

    public ResolvedParametrizedTypeMirror resolveTypeVariable(TypeElement currentTypeElement,
                                                              TypeVariable typeVar) {
        int index = findFormalParameterIndex(currentTypeElement, typeVar);
        return resolveTypeVariableByIndex(hierarchy, currentTypeElement, index);
    }

    private ResolvedParametrizedTypeMirror resolveTypeVariableByIndex(LinerHierarchy<TypeElement> hierarchy,
                                                                      TypeElement currentTypeElement,
                                                                      int index) {
        return hierarchy.findFirstUnder(currentTypeElement)
                .map(subClass -> resolveTypeVariableByIndexInSubClass(subClass, index))
                .orElseGet(() -> resolveTypeVariableByIndexInRootActualTypeArguments(index));
    }

    private ResolvedParametrizedTypeMirror resolveTypeVariableByIndexInRootActualTypeArguments(int index) {
        if (actualTypeArguments.size() <= index) {
            TypeElement rootElement = hierarchy.getHighest();
            throw new LensProcessingException(MessageFactory.actualTypeParameterNotFound((DeclaredType) rootElement.asType(), index));
        }
        return actualTypeArguments.get(index);
    }

    private ResolvedParametrizedTypeMirror resolveTypeVariableByIndexInSubClass(TypeElement subClass, int index) {
        DeclaredType superClassType = (DeclaredType) subClass.getSuperclass();
        TypeMirror actualParameterType = findActualParameterByIndex(superClassType, index);
        return resolveType(subClass, actualParameterType);
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

package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import dev.khbd.lens4j.processor.LensProcessingException;
import dev.khbd.lens4j.processor.MessageFactory;
import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * @author Sergei_Khadanovich
 */
public class TypeNameBuilder {

    private final Types typeUtils;

    public TypeNameBuilder(Types typeUtils) {
        this.typeUtils = typeUtils;
    }

    /**
     * Build type name by resolved type mirror
     *
     * @param resolveTypeMirror resolved type mirror
     * @return type name
     */
    public TypeName buildTypeName(ResolvedParametrizedTypeMirror resolveTypeMirror) {
        return buildTypeName(resolveTypeMirror, false);
    }

    private TypeName buildTypeName(ResolvedParametrizedTypeMirror resolvedTypeMirror, boolean inArray) {
        TypeMirror typeMirror = resolvedTypeMirror.getTypeMirror();
        if (typeMirror.getKind().isPrimitive()) {
            return buildPrimitiveTypeName(typeMirror, inArray);
        } else if (typeMirror.getKind() == TypeKind.ARRAY) {
            return buildArrayTypeName(resolvedTypeMirror);
        } else if (typeMirror.getKind() == TypeKind.DECLARED) {
            return buildDeclaredTypeName(resolvedTypeMirror);
        }
        throw new LensProcessingException(MessageFactory.unsupportedTypeKind(typeMirror.getKind()));
    }

    private TypeName buildDeclaredTypeName(ResolvedParametrizedTypeMirror resolvedTypeMirror) {
        if (resolvedTypeMirror.isParametrized()) {
            TypeName[] typeNames = resolvedTypeMirror.getActualTypeArguments().stream()
                    .map(type -> buildTypeName(type, false))
                    .toArray(TypeName[]::new);

            DeclaredType declaredType = (DeclaredType) resolvedTypeMirror.getTypeMirror();
            TypeElement typeElement = (TypeElement) declaredType.asElement();

            return ParameterizedTypeName.get(
                    ClassName.get(typeElement),
                    typeNames
            );
        } else {
            DeclaredType declaredType = (DeclaredType) resolvedTypeMirror.getTypeMirror();
            TypeElement typeElement = (TypeElement) declaredType.asElement();
            return ClassName.get(typeElement);
        }
    }

    private ArrayTypeName buildArrayTypeName(ResolvedParametrizedTypeMirror resolvedTypeMirror) {
        ResolvedParametrizedTypeMirror componentTypeMirror = resolvedTypeMirror.getFirstActualTypeArgument();
        return ArrayTypeName.of(buildTypeName(componentTypeMirror, true));
    }

    private TypeName buildPrimitiveTypeName(TypeMirror typeMirror, boolean inArray) {
        if (inArray) {
            return TypeName.get(typeMirror);
        } else {
            TypeElement boxedTypeElement = typeUtils.boxedClass((PrimitiveType) typeMirror);
            return TypeName.get(boxedTypeElement.asType());
        }
    }
}

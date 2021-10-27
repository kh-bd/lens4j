package dev.khbd.lens4j.processor;

import com.google.common.base.CaseFormat;
import dev.khbd.lens4j.common.Method;
import dev.khbd.lens4j.common.Path;
import dev.khbd.lens4j.common.PathParser;
import dev.khbd.lens4j.common.PathVisitor;
import dev.khbd.lens4j.common.Property;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.meta.LensMeta;
import dev.khbd.lens4j.processor.meta.LensPartMeta;
import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;
import dev.khbd.lens4j.processor.path.PathStructureValidator;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sergei_Khadanovich
 */
public class LensMetaBuilder {

    private static final String LENS_NAME_SUFFIX = "LENS";

    private final TypeElement rootClassElement;

    public LensMetaBuilder(TypeElement rootClassElement) {
        this.rootClassElement = rootClassElement;
    }

    public LensMeta build(Lens annotation) {
        Path path = PathParser.getInstance().parse(annotation.path());

        if (!PathStructureValidator.validate(path)) {
            throw new LensProcessingException(MessageFactory.pathIsIncorrect(rootClassElement));
        }

        LensMeta meta = new LensMeta(makeLensName(annotation, path), annotation.type(), getLensModifiers(annotation));
        path.visit(new LensPartResolver(meta));
        return meta;
    }

    class LensPartResolver implements PathVisitor {

        private final LensMeta meta;

        private ResolvedParametrizedTypeMirror lastResolvedType =
                new ResolvedParametrizedTypeMirror(rootClassElement.asType());

        public LensPartResolver(LensMeta meta) {
            this.meta = meta;
        }

        @Override
        public void visitProperty(Property property) {
            TypeElement currentClassElement = resolveTypeElement(lastResolvedType.getTypeMirror());

            VariableElement fieldElement = findField(currentClassElement, property.getName());
            ResolvedParametrizedTypeMirror fieldType =
                    resolveType(currentClassElement, lastResolvedType.getActualTypeArguments(),
                            fieldElement, fieldElement.asType());

            LensPartMeta part = new LensPartMeta(lastResolvedType, fieldType, property.getName());
            meta.addLensPart(part);

            lastResolvedType = fieldType;
        }

        @Override
        public void visitMethod(Method method) {
            TypeElement currentClassElement = resolveTypeElement(lastResolvedType.getTypeMirror());

            ExecutableElement methodElement = findMethod(currentClassElement, method.getName());
            ResolvedParametrizedTypeMirror methodReturnType =
                    resolveType(currentClassElement, lastResolvedType.getActualTypeArguments(),
                            methodElement, methodElement.getReturnType());

            LensPartMeta part = new LensPartMeta(lastResolvedType, methodReturnType, method.getName());
            meta.addLensPart(part.withShape(LensPartMeta.Shape.METHOD));

            lastResolvedType = methodReturnType;
        }

        @Override
        public void finish() {
            if (meta.getLensType() == LensType.READ_WRITE
                    && meta.getLastLensPart().getShape() == LensPartMeta.Shape.METHOD) {
                throw new LensProcessingException(MessageFactory.methodAtWrongPosition(rootClassElement));
            }
        }

        private VariableElement findField(TypeElement classElement, String fieldName) {
            return ProcessorUtils.findNonStaticFieldByName(classElement, fieldName)
                    .orElseThrow(() -> new LensProcessingException(MessageFactory.fieldNotFound(classElement, fieldName)));
        }

        private ExecutableElement findMethod(TypeElement classElement, String methodName) {
            return ProcessorUtils.findNonStaticMethodsByName(classElement, methodName)
                    .stream()
                    .filter(m -> m.getReturnType().getKind() != TypeKind.VOID)
                    .filter(m -> m.getParameters().isEmpty())
                    .filter(m -> !m.getModifiers().contains(Modifier.PRIVATE))
                    .findFirst()
                    .orElseThrow(() -> new LensProcessingException(MessageFactory.methodNotFound(classElement, methodName)));
        }

        private ResolvedParametrizedTypeMirror resolveType(TypeElement rootClass,
                                                           List<ResolvedParametrizedTypeMirror> actualTypeArguments,
                                                           Element element,
                                                           TypeMirror type) {
            return new TypeResolver(rootClass, actualTypeArguments)
                    .resolveType((TypeElement) element.getEnclosingElement(), type);
        }

        private TypeElement resolveTypeElement(TypeMirror typeMirror) {
            if (typeMirror.getKind() != TypeKind.DECLARED) {
                throw new LensProcessingException(MessageFactory.nonDeclaredTypeFound(rootClassElement));
            }
            DeclaredType declaredType = (DeclaredType) typeMirror;
            return (TypeElement) declaredType.asElement();
        }
    }

    private String makeLensName(Lens lens, Path path) {
        if (StringUtils.isNotBlank(lens.lensName())) {
            return lens.lensName();
        }
        return deriveLensNameByPath(path, lens.type());
    }

    private String deriveLensNameByPath(Path path, LensType type) {
        LensNameAccumulator accumulator = new LensNameAccumulator(type);
        path.visit(accumulator);
        return accumulator.getLensName();
    }

    private static class LensNameAccumulator implements PathVisitor {

        private final LensType lensType;
        private final StringBuilder builder = new StringBuilder();

        public LensNameAccumulator(LensType lensType) {
            this.lensType = lensType;
        }

        @Override
        public void visitProperty(Property property) {
            if (builder.length() != 0) {
                builder.append("_");
            }
            builder.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, property.getName()));
        }

        @Override
        public void finish() {
            builder.append("_");
            builder.append(lensType);
            builder.append("_");
            builder.append(LENS_NAME_SUFFIX);
        }

        private String getLensName() {
            return builder.toString();
        }
    }

    private Set<Modifier> getLensModifiers(Lens lens) {
        Set<Modifier> modifiers = EnumSet.noneOf(Modifier.class);

        modifiers.add(Modifier.FINAL);
        modifiers.add(Modifier.STATIC);

        if (lens.accessLevel() == Lens.AccessLevel.PUBLIC) {
            modifiers.add(Modifier.PUBLIC);
        }

        return modifiers;
    }

}

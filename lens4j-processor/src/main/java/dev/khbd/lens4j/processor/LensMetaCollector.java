package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.meta.FactoryId;
import dev.khbd.lens4j.processor.meta.LensMeta;
import dev.khbd.lens4j.processor.meta.LensPartMeta;
import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;
import dev.khbd.lens4j.processor.path.Method;
import dev.khbd.lens4j.processor.path.Path;
import dev.khbd.lens4j.processor.path.PathParser;
import dev.khbd.lens4j.processor.path.PathStructureValidator;
import dev.khbd.lens4j.processor.path.PathVisitor;
import dev.khbd.lens4j.processor.path.Property;
import dev.khbd.lens4j.processor.util.ProcessorUtils;
import dev.khbd.lens4j.processor.util.StringUtils;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Lens metadata builder.
 *
 * @author Sergei_Khadanovich
 */
@RequiredArgsConstructor
public class LensMetaCollector {

    private static final String LENS_NAME_SUFFIX = "LENS";
    private static final List<String> SUPPORTED_ARRAYS_PROPERTIES = Collections.singletonList("length");

    private final TypeElement rootClassElement;
    private final Types typeUtil;

    public LensMeta collect(FactoryId id, Lens annotation) {
        Path path = PathParser.getInstance().parse(annotation.path());

        if (!PathStructureValidator.validate(path)) {
            throw new LensProcessingException(MessageFactory.pathIsIncorrect(rootClassElement));
        }

        LensMeta.LensMetaBuilder metaBuilder = LensMeta.builder()
                .name(makeLensName(annotation, path))
                .type(annotation.type())
                .modifiers(getLensModifiers(annotation));

        path.visit(new LensPartResolver(id, metaBuilder));
        return metaBuilder.build();
    }

    @RequiredArgsConstructor
    class LensPartResolver implements PathVisitor {

        private final FactoryId factoryId;
        private final LensMeta.LensMetaBuilder builder;

        private ResolvedParametrizedTypeMirror lastResolvedType =
                new ResolvedParametrizedTypeMirror(rootClassElement.asType());

        @Override
        public void visitProperty(Property property) {
            if (lastResolvedTypeIsArray()) {
                if (!SUPPORTED_ARRAYS_PROPERTIES.contains(property.name())) {
                    throw new LensProcessingException(MessageFactory.arraysPropertyIsNotSupported(property.name()));
                }
                builder.part(makeArrayLensPartMeta(property));
            } else {
                builder.part(makeTypeLensPartMeta(property));
            }
        }

        @Override
        public void visitMethod(Method method) {
            TypeElement currentClassElement = resolveTypeElement(lastResolvedType.getTypeMirror());

            ExecutableElement methodElement = findMethod(currentClassElement, method.name());
            verifyMethod(methodElement);
            ResolvedParametrizedTypeMirror methodReturnType =
                    resolveType(currentClassElement, lastResolvedType.getActualTypeArguments(),
                            methodElement, methodElement.getReturnType());

            LensPartMeta part = LensPartMeta.builder()
                    .sourceType(lastResolvedType)
                    .targetType(methodReturnType)
                    .name(method.name())
                    .shape(LensPartMeta.Shape.METHOD)
                    .build();

            builder.part(part);

            lastResolvedType = methodReturnType;
        }

        private void verifyMethod(ExecutableElement method) {
            List<? extends TypeParameterElement> params = method.getTypeParameters();
            if (!params.isEmpty()) {
                throw new LensProcessingException(MessageFactory.parametrizedMethodIsNotAllowed(method));
            }
        }

        @Override
        public void finish() {
            LensMeta meta = builder.build();
            if (meta.getType() == LensType.READ_WRITE) {
                verifyRecordPropertyAtLastPosition(meta);
                verifyMethodAtLastPosition(meta);
            }
        }

        private void verifyMethodAtLastPosition(LensMeta meta) {
            if (meta.getLastPart().getShape() == LensPartMeta.Shape.METHOD) {
                throw new LensProcessingException(MessageFactory.methodAtWrongPosition(rootClassElement));
            }
        }

        private void verifyRecordPropertyAtLastPosition(LensMeta meta) {
            TypeMirror sourceMirror = meta.getLastPart().getSourceType().getTypeMirror();
            if (sourceMirror.getKind() == TypeKind.DECLARED) {
                DeclaredType declaredType = (DeclaredType) sourceMirror;
                if (declaredType.asElement().getKind() == ElementKind.RECORD) {
                    throw new LensProcessingException(MessageFactory.recordPropertyAtWrongPosition(rootClassElement));
                }
            }
        }

        private LensPartMeta makeArrayLensPartMeta(Property property) {
            ResolvedParametrizedTypeMirror fieldType = new ResolvedParametrizedTypeMirror(
                    typeUtil.getPrimitiveType(TypeKind.INT)
            );
            LensPartMeta part = LensPartMeta.builder()
                    .sourceType(lastResolvedType)
                    .targetType(fieldType)
                    .name(property.name())
                    .shape(LensPartMeta.Shape.FIELD)
                    .build();
            lastResolvedType = fieldType;
            return part;
        }

        private LensPartMeta makeTypeLensPartMeta(Property property) {
            TypeElement currentClassElement = resolveTypeElement(lastResolvedType.getTypeMirror());

            VariableElement fieldElement = findField(currentClassElement, property.name());
            ResolvedParametrizedTypeMirror fieldType =
                    resolveType(currentClassElement, lastResolvedType.getActualTypeArguments(),
                            fieldElement, fieldElement.asType());

            LensPartMeta.LensPartMetaBuilder partBuilder = LensPartMeta.builder()
                    .sourceType(lastResolvedType)
                    .targetType(fieldType)
                    .name(property.name());

            if (currentClassElement.getKind() == ElementKind.RECORD) {
                partBuilder.shape(LensPartMeta.Shape.METHOD);
            } else {
                if(isPackageSameAsFactory(currentClassElement)) {
                    if (!ProcessorUtils.isPrivate(fieldElement)) {
                        partBuilder.shape(LensPartMeta.Shape.FIELD);
                    }
                } else {
                    if (ProcessorUtils.isPublic(fieldElement)) {
                        partBuilder.shape(LensPartMeta.Shape.FIELD);
                    }
                }
            }

            lastResolvedType = fieldType;

            return partBuilder.build();
        }

        private boolean isPackageSameAsFactory(TypeElement element) {
            TypeElement topElement = ProcessorUtils.getTopLevelClass(element);
            Element enclosing = topElement.getEnclosingElement();
            if (enclosing instanceof PackageElement pe) {
                return pe.getQualifiedName().contentEquals(factoryId.getPackageName());
            }
            return false;
        }

        private boolean lastResolvedTypeIsArray() {
            return lastResolvedType.getTypeMirror().getKind() == TypeKind.ARRAY;
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
        if (!StringUtils.isBlank(lens.lensName())) {
            return lens.lensName();
        }
        return deriveLensNameByPath(path, lens.type());
    }

    private String deriveLensNameByPath(Path path, LensType type) {
        LensNameAccumulator accumulator = new LensNameAccumulator(type);
        path.visit(accumulator);
        return accumulator.getLensName();
    }

    @RequiredArgsConstructor
    private static class LensNameAccumulator implements PathVisitor {

        private final LensType lensType;
        private final StringBuilder builder = new StringBuilder();

        @Override
        public void visitProperty(Property property) {
            visitNamed(property.name());
        }

        @Override
        public void visitMethod(Method method) {
            visitNamed(method.name());
        }

        private void visitNamed(String name) {
            if (!builder.isEmpty()) {
                builder.append("_");
            }
            builder.append(StringUtils.toSnakeCase(name));
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

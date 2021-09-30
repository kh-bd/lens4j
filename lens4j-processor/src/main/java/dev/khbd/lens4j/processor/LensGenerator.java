package dev.khbd.lens4j.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import dev.khbd.lens4j.core.annotations.LensType;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Map;

/**
 * Lens generator.
 *
 * @author Alexey_Bodyak
 */
public class LensGenerator {

    private static final String UNSUPPORTED_METHOD_MSG = "Can not create instance of factory class";

    private final Types typeUtils;

    public LensGenerator(Types typeUtils) {
        this.typeUtils = typeUtils;
    }

    /**
     * Generate factory source file.
     *
     * @param factoryMeta factory meta
     * @return generated java file
     */
    public JavaFile generate(FactoryMeta factoryMeta) {
        return JavaFile.builder(factoryMeta.getPackageName(), makeType(factoryMeta)).build();
    }

    private TypeSpec makeType(FactoryMeta factoryMeta) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(factoryMeta.getFactoryName());
        builder.addModifiers(factoryMeta.getFactoryModifiers().toArray(new Modifier[0]));
        builder.addAnnotation(makeGeneratedAnnotation());
        builder.addMethod(makeConstructor());
        for (LensMeta lensMeta : factoryMeta.getLenses()) {
            builder.addField(makeLens(lensMeta));
        }
        return builder.build();
    }

    private AnnotationSpec makeGeneratedAnnotation() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", LensProcessor.class.getCanonicalName())
                .build();
    }

    private FieldSpec makeLens(LensMeta lensMeta) {
        return FieldSpec.builder(makeLensType(lensMeta), lensMeta.getLensName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer(makeExpression(lensMeta))
                .build();
    }

    private CodeBlock makeExpression(LensMeta lensMeta) {
        CodeBlock.Builder builder = CodeBlock.builder();

        if (lensMeta.isSinglePart()) {
            builder.add(makeLensCodeBlock(lensMeta.getFirstLensPart(), lensMeta.getLensType()));
            return builder.build();
        }

        builder.add(makeLensCodeBlock(lensMeta.getFirstLensPart(), LensType.READ));
        for (LensPartMeta part : lensMeta.getLensPartsWithoutEnds()) {
            builder.add(".andThen($L)", makeLensCodeBlock(part, LensType.READ));
        }
        builder.add(".andThen($L)", makeLensCodeBlock(lensMeta.getLastLensPart(), lensMeta.getLensType()));
        return builder.build();
    }

    private CodeBlock makeLensCodeBlock(LensPartMeta lensPartMeta, LensType lensType) {
        Map<String, Object> params = Map.of(
                "lenses", ClassName.get(Lenses.class),
                "sourceType", TypeName.get(lensPartMeta.getSourceType()),
                "fieldName", StringUtils.capitalize(lensPartMeta.getPropertyName())
        );
        return CodeBlock.builder()
                .addNamed(getLensCodeBlockTemplate(lensType), params)
                .build();
    }

    private String getLensCodeBlockTemplate(LensType lensType) {
        return lensType == LensType.READ
                ? getReadLensCodeBlockTemplate()
                : getReadWriteLensCodeBlockTemplate();
    }

    private String getReadLensCodeBlockTemplate() {
        return "$lenses:T.readLens($sourceType:T::get$fieldName:L)";
    }

    private String getReadWriteLensCodeBlockTemplate() {
        return "$lenses:T.readWriteLens($sourceType:T::get$fieldName:L, $sourceType:T::set$fieldName:L)";
    }

    private TypeName makeLensType(LensMeta lensMeta) {
        if (lensMeta.getLensType() == LensType.READ) {
            return makeLensReadType(lensMeta);
        }
        return makeLensReadWriteType(lensMeta);
    }

    private TypeName makeLensReadType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadLens.class),
                TypeName.get(lensMeta.getFirstLensPart().getSourceType()),
                resolvePropertyType(lensMeta.getLastLensPart().getPropertyType())
        );
    }

    private TypeName makeLensReadWriteType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadWriteLens.class),
                TypeName.get(lensMeta.getFirstLensPart().getSourceType()),
                resolvePropertyType(lensMeta.getLastLensPart().getPropertyType())
        );
    }

    private TypeName resolvePropertyType(TypeMirror typeMirror) {
        TypeKind typeKind = typeMirror.getKind();
        if (typeKind.isPrimitive()) {
            return TypeName.get(typeUtils.boxedClass((PrimitiveType) typeMirror).asType());
        }
        return TypeName.get(typeMirror);
    }

    private MethodSpec makeConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("throw new $T($S)",
                        ClassName.get(UnsupportedOperationException.class),
                        UNSUPPORTED_METHOD_MSG
                )
                .build();
    }
}

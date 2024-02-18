package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.LensProcessor;
import dev.khbd.lens4j.processor.LexicalScope;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.lens4j.processor.meta.LensMeta;
import dev.khbd.lens4j.processor.meta.LensPartMeta;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Lens generator.
 *
 * @author Sergei_Khadanovich
 */
public class InlinedLensFactoryGenerator implements LensFactoryGenerator {

    private static final String PROPERTY_ARGUMENT_NAME = "property";
    private static final String SOURCE_ARGUMENT_NAME = "object";

    private static final String UNSUPPORTED_METHOD_MSG = "Can not create instance of factory class";

    private final TypeNameBuilder typeNameBuilder;
    private final Map<LensPartMeta.Shape, LensPartGenerationStrategy> strategies;

    public InlinedLensFactoryGenerator(Types typeUtils) {
        typeNameBuilder = new TypeNameBuilder(typeUtils);

        strategies = new EnumMap<>(LensPartMeta.Shape.class);
        strategies.put(LensPartMeta.Shape.ACCESSORS, new AccessorsLensPartGenerationStrategy());
        strategies.put(LensPartMeta.Shape.METHOD, new MethodLensPartGenerationStrategy());
        strategies.put(LensPartMeta.Shape.FIELD, new FieldLensPartGenerationStrategy());
    }

    @Override
    public JavaFile generate(FactoryMeta factoryMeta) {
        return JavaFile.builder(factoryMeta.getId().getPackageName(), makeType(factoryMeta)).build();
    }

    private TypeSpec makeType(FactoryMeta factoryMeta) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(factoryMeta.getId().getFactoryName());
        builder.addModifiers(factoryMeta.getModifiers().toArray(new Modifier[0]));
        builder.addAnnotation(makeGeneratedAnnotation());
        builder.addMethod(factoryConstructor());
        for (LensMeta lensMeta : factoryMeta.getLenses()) {
            builder.addField(lensField(lensMeta));
        }
        return builder.build();
    }

    private AnnotationSpec makeGeneratedAnnotation() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", LensProcessor.class.getCanonicalName())
                .build();
    }

    private MethodSpec factoryConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("throw new $T($S)",
                        ClassName.get(UnsupportedOperationException.class),
                        UNSUPPORTED_METHOD_MSG
                )
                .build();
    }

    private FieldSpec lensField(LensMeta lensMeta) {
        TypeName lensType = makeLensType(lensMeta);
        return FieldSpec.builder(lensType, lensMeta.getName())
                .addModifiers(lensMeta.getModifiers().toArray(new Modifier[0]))
                .initializer("$L", anonymousLensClass(lensType, lensMeta))
                .build();
    }

    private TypeSpec anonymousLensClass(TypeName lensType, LensMeta lensMeta) {
        TypeSpec.Builder builder = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(lensType)
                .addMethod(readLensMethod(lensMeta));
        if (lensMeta.getType() == LensType.READ_WRITE) {
            builder.addMethod(readWriteMethod(lensMeta));
        }
        return builder.build();
    }

    private MethodSpec readLensMethod(LensMeta lensMeta) {
        return MethodSpec.methodBuilder("get")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .returns(typeNameBuilder.buildTypeName(lensMeta.getLastPart().getTargetType()))
                .addParameter(typeNameBuilder.buildTypeName(lensMeta.getFirstPart().getSourceType()), SOURCE_ARGUMENT_NAME)
                .addCode(readLensLogic(lensMeta))
                .build();
    }

    private MethodSpec readWriteMethod(LensMeta lensMeta) {
        return MethodSpec.methodBuilder("set")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(typeNameBuilder.buildTypeName(lensMeta.getFirstPart().getSourceType()), SOURCE_ARGUMENT_NAME)
                .addParameter(typeNameBuilder.buildTypeName(lensMeta.getLastPart().getTargetType()), PROPERTY_ARGUMENT_NAME)
                .addCode(writeLensLogic(lensMeta))
                .build();
    }

    private CodeBlock readLensLogic(LensMeta lensMeta) {
        LexicalScope lexicalScope = new LexicalScope(SOURCE_ARGUMENT_NAME);

        String sourceName = SOURCE_ARGUMENT_NAME;

        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add(nonNullReadCodeBlock(sourceName));

        for (LensPartMeta part : lensMeta.getPartsWithoutLast()) {
            String propertyName = lexicalScope.add(part.getName());
            builder.addStatement("$T $N = $L", typeNameBuilder.buildTypeName(part.getTargetType()), propertyName, propertyRead(sourceName, part));
            builder.add(nonNullReadCodeBlock(propertyName));

            sourceName = propertyName;
        }

        LensPartMeta lastPart = lensMeta.getLastPart();
        builder.add(CodeBlock.builder().add("return $L;", propertyRead(sourceName, lastPart)).build());

        return builder.build();
    }

    private CodeBlock writeLensLogic(LensMeta lensMeta) {
        LexicalScope lexicalScope = new LexicalScope(SOURCE_ARGUMENT_NAME, PROPERTY_ARGUMENT_NAME);

        String sourceName = SOURCE_ARGUMENT_NAME;

        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add(nonNullWriteCodeBlock(sourceName));

        for (LensPartMeta part : lensMeta.getPartsWithoutLast()) {
            String propertyName = lexicalScope.add(part.getName());
            builder.addStatement("$T $N = $L", typeNameBuilder.buildTypeName(part.getTargetType()), propertyName, propertyRead(sourceName, part));
            builder.add(nonNullWriteCodeBlock(propertyName));

            sourceName = propertyName;
        }

        LensPartMeta lastPart = lensMeta.getLastPart();
        builder.addStatement(propertyWrite(sourceName, lastPart));

        return builder.build();
    }

    private CodeBlock nonNullReadCodeBlock(String varName) {
        return CodeBlock.builder()
                .beginControlFlow("if ($N == null)", varName)
                .addStatement("return null")
                .endControlFlow()
                .build();
    }

    private CodeBlock nonNullWriteCodeBlock(String varName) {
        return CodeBlock.builder()
                .beginControlFlow("if ($N == null)", varName)
                .addStatement("return")
                .endControlFlow()
                .build();
    }

    private CodeBlock propertyRead(String sourceName, LensPartMeta meta) {
        LensPartMeta.Shape shape = meta.getShape();
        return findStrategy(shape).generateRead(sourceName, meta);
    }

    private CodeBlock propertyWrite(String sourceName, LensPartMeta meta) {
        LensPartMeta.Shape shape = meta.getShape();
        return findStrategy(shape).generateWrite(sourceName, meta, PROPERTY_ARGUMENT_NAME);
    }

    private LensPartGenerationStrategy findStrategy(LensPartMeta.Shape shape) {
        LensPartGenerationStrategy strategy = strategies.get(shape);
        if (Objects.isNull(strategy)) {
            throw new RuntimeException(String.format("Lens part generation strategy was not found for shape = %s", shape));
        }
        return strategy;
    }

    private TypeName makeLensType(LensMeta lensMeta) {
        if (lensMeta.getType() == LensType.READ) {
            return makeLensReadType(lensMeta);
        }
        return makeLensReadWriteType(lensMeta);
    }

    private TypeName makeLensReadType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadLens.class),
                typeNameBuilder.buildTypeName(lensMeta.getFirstPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastPart().getTargetType())
        );
    }

    private TypeName makeLensReadWriteType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadWriteLens.class),
                typeNameBuilder.buildTypeName(lensMeta.getFirstPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastPart().getTargetType())
        );
    }

    /**
     * Lens part generation strategy.
     *
     * @author Sergei_Khadanovich
     */
    interface LensPartGenerationStrategy {

        /**
         * Generate code to read property on source instance.
         *
         * @param sourceName source variable name
         * @param meta       lens part meta data
         * @return code block to read property
         */
        CodeBlock generateRead(String sourceName, LensPartMeta meta);

        /**
         * Generate code to write property into source instance.
         *
         * @param sourceName   source variable name
         * @param meta         lens part meta data
         * @param propertyName property variable name
         * @return code block to write property
         */
        CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName);
    }

    /**
     * Field part generation strategy.
     *
     * @author Sergei_Khadanovich
     */
    private static class FieldLensPartGenerationStrategy implements LensPartGenerationStrategy {

        @Override
        public CodeBlock generateRead(String sourceName, LensPartMeta meta) {
            return CodeBlock.builder()
                    .add("$L.$L", sourceName, meta.getName())
                    .build();
        }

        @Override
        public CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName) {
            return CodeBlock.builder()
                    .add("$L.$L = $L", sourceName, meta.getName(), propertyName)
                    .build();
        }
    }

    /**
     * Accessors part generation strategy.
     *
     * @author Sergei_Khadanovich
     */
    private static class AccessorsLensPartGenerationStrategy implements LensPartGenerationStrategy {

        @Override
        public CodeBlock generateRead(String sourceName, LensPartMeta meta) {
            return CodeBlock.builder()
                    .add("$L.get$L()", sourceName, StringUtils.capitalize(meta.getName()))
                    .build();
        }

        @Override
        public CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName) {
            return CodeBlock.builder()
                    .add("$L.set$L($L)", sourceName, StringUtils.capitalize(meta.getName()), propertyName)
                    .build();
        }
    }

    /**
     * Method part generation strategy.
     *
     * @author Sergei_Khadanovich
     */
    private static class MethodLensPartGenerationStrategy implements LensPartGenerationStrategy {

        @Override
        public CodeBlock generateRead(String sourceName, LensPartMeta meta) {
            return CodeBlock.builder()
                    .add("$L.$L()", sourceName, meta.getName())
                    .build();
        }

        @Override
        public CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName) {
            throw new IllegalArgumentException("Method-based generation strategy can not be used in write position");
        }
    }

}

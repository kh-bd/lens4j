package dev.khbd.lens4j.processor;

import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.JavaFile;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Annotation processor for generating lens.
 *
 * @author Alexey_Bodyak
 */
@AutoService(Processor.class)
public class LensProcessor extends AbstractProcessor {

    private static final String DEFAULT_FACTORY_SUFFIX = "Lenses";
    private static final String LENS_NAME_SUFFIX = "LENS";

    private LensGenerator lensGenerator;
    private Filer filer;
    private Logger logger;
    private Elements elementUtil;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.lensGenerator = new LensGenerator(processingEnv.getTypeUtils());
        this.logger = new Logger(processingEnv.getMessager());
        this.elementUtil = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(GenLenses.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processElements(roundEnv) == ProcessResult.ERROR;
    }

    private ProcessResult processElements(RoundEnvironment roundEnv) {
        Set<? extends Element> lensElements = findLensElements(roundEnv);
        try {
            for (Element element : lensElements) {
                FactoryMeta factoryMeta = makeFactoryMeta(element);
                JavaFile file = lensGenerator.generate(factoryMeta);
                writeFile(file);
            }
        } catch (LensProcessingException e) {
            logger.error(e.getError());
            return ProcessResult.ERROR;
        }
        return ProcessResult.GENERATED;
    }

    private FactoryMeta makeFactoryMeta(Element element) {
        if (element.getKind() == ElementKind.CLASS) {
            return makeFactoryMetaFromClassElement((TypeElement) element);
        }
        throw new LensProcessingException(MessageFactory.genLensNotAllowedHere(element));
    }

    private FactoryMeta makeFactoryMetaFromClassElement(TypeElement classElement) {
        verifyClass(classElement);

        GenLenses annotation = classElement.getAnnotation(GenLenses.class);
        return makeFactoryMetaFromClassElement(classElement, annotation);
    }

    private void verifyClass(TypeElement classElement) {
        if (!classElement.getTypeParameters().isEmpty()) {
            throw new LensProcessingException(MessageFactory.genLensNotAllowedOnGenericClasses(classElement));
        }
    }

    private FactoryMeta makeFactoryMetaFromClassElement(TypeElement classElement, GenLenses annotation) {
        checkLensPath(classElement, List.of(annotation.lenses()));

        FactoryMeta factory = new FactoryMeta(getPackage(classElement),
                makeFactoryName(classElement, annotation), getClassModifiers(classElement));

        for (Lens lens : annotation.lenses()) {
            factory.addLens(makeLensMeta(classElement, lens));
        }
        checkLensNames(classElement, factory.getLenses());
        return factory;
    }

    private void checkLensNames(Element classElement, List<LensMeta> lenses) {
        Map<String, List<LensMeta>> lensNames = lenses.stream().collect(Collectors.groupingBy(LensMeta::getLensName));
        for (Map.Entry<String, List<LensMeta>> entry : lensNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new LensProcessingException(MessageFactory.existNotUniqueLensName(classElement));
            }
        }
    }

    private void checkLensPath(Element classElement, List<Lens> lenses) {
        for (Lens lens : lenses) {
            if (StringUtils.isBlank(lens.path())) {
                throw new LensProcessingException(MessageFactory.pathIsEmpty(classElement));
            }
        }
    }

    private String makeFactoryName(TypeElement classElement, GenLenses annotation) {
        String factoryName = annotation.factoryName();
        if (StringUtils.isBlank(factoryName)) {
            return deriveFactoryName(classElement);
        }
        return StringUtils.capitalize(factoryName);
    }

    private String deriveFactoryName(TypeElement classElement) {
        String joinedClassNames = ProcessorUtils.getNestedHierarchy(classElement).stream()
                .map(Element::getSimpleName)
                .collect(Collectors.joining());
        return joinedClassNames + DEFAULT_FACTORY_SUFFIX;
    }

    private Set<Modifier> getClassModifiers(TypeElement classElement) {
        Set<Modifier> modifiers = new HashSet<>();
        modifiers.add(Modifier.FINAL);

        TypeElement topLevelClass = ProcessorUtils.getTopLevelClass(classElement);
        if (topLevelClass.getModifiers().contains(Modifier.PUBLIC)) {
            modifiers.add(Modifier.PUBLIC);
        }
        return modifiers;
    }

    private String getPackage(TypeElement classElement) {
        return elementUtil.getPackageOf(classElement).toString();
    }

    private LensMeta makeLensMeta(TypeElement classElement, Lens annotation) {
        String path = annotation.path();
        String[] properties = path.split("\\.");
        String lensName = makeLensName(annotation.lensName(), annotation.type(), properties);

        LensMeta meta = new LensMeta(lensName, annotation.type());
        TypeElement currentClassElement = classElement;

        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];

            Element field = findFieldByName(currentClassElement, property);
            TypeMirror fieldType = resolveFieldType(currentClassElement, field);

            LensPartMeta part = new LensPartMeta(currentClassElement.asType(), fieldType, property);
            meta.addLensPart(part);

            if (i != properties.length - 1) { // not last element in path
                if (fieldType.getKind() != TypeKind.DECLARED) {
                    throw new LensProcessingException(MessageFactory.nonDeclaredTypeFound(classElement));
                }
                DeclaredType declaredType = (DeclaredType) fieldType;
                currentClassElement = (TypeElement) declaredType.asElement();
            }
        }

        return meta;
    }

    private TypeMirror resolveFieldType(TypeElement classElement, Element field) {
        TypeMirror fieldType = field.asType();
        if (fieldType.getKind() == TypeKind.TYPEVAR) {
            TypeVariable typeVariable = (TypeVariable) fieldType;
            return new TypeVariableResolver(classElement)
                    .resolveType((TypeElement) field.getEnclosingElement(),
                            typeVariable);
        }
        return fieldType;
    }

    private String makeLensName(String userLensName, LensType lensType, String[] properties) {
        if (StringUtils.isNotBlank(userLensName)) {
            return userLensName;
        }
        return deriveLensNameByPath(properties, lensType);
    }

    private String deriveLensNameByPath(String[] properties, LensType lensType) {
        return String.format("%s_%s_%s", joinProperties(properties), lensType, LENS_NAME_SUFFIX);
    }

    private String joinProperties(String[] properties) {
        return Stream.of(properties)
                .map(it -> CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, it))
                .collect(Collectors.joining("_"));
    }

    private Element findFieldByName(TypeElement classElement, String fieldName) {
        return ProcessorUtils.findNonStaticFieldByName(classElement, fieldName)
                .orElseThrow(() -> new LensProcessingException(MessageFactory.fieldNotFound(classElement, fieldName)));
    }

    private Set<? extends Element> findLensElements(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.class);
    }

    private void writeFile(JavaFile javaFile) {
        try {
            javaFile.writeTo(filer);
        } catch (IOException ioe) {
            throw new RuntimeException("Error during java sources generation", ioe);
        }
    }

    enum ProcessResult {
        GENERATED,
        ERROR
    }
}

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

    private static final String DEFAULT_FACTORY_NAME = "Lenses";
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
        if (classElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
            throw new LensProcessingException(MessageFactory.genLensNotAllowedOnInnerClasses(classElement));
        }
        if (!classElement.getTypeParameters().isEmpty()) {
            throw new LensProcessingException(MessageFactory.genLensNotAllowedOnGenericClasses(classElement));
        }
    }

    private FactoryMeta makeFactoryMetaFromClassElement(Element classElement, GenLenses annotation) {
        checkLensPath(classElement, List.of(annotation.lenses()));

        FactoryMeta factory = new FactoryMeta(extractPackageName(classElement),
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
                throw new LensProcessingException(Message.of("Lens names for type should be unique", classElement));
            }
        }
    }

    private void checkLensPath(Element classElement, List<Lens> lenses) {
        for (Lens lens : lenses) {
            if (StringUtils.isBlank(lens.path())) {
                throw new LensProcessingException(Message.of("Lens path should be not empty", classElement));
            }
        }
    }

    private String makeFactoryName(Element classElement, GenLenses annotation) {
        String factoryName = annotation.factoryName();
        if (StringUtils.isBlank(factoryName)) {
            return String.format("%s%s", classElement.getSimpleName(), DEFAULT_FACTORY_NAME);
        }
        return StringUtils.capitalize(factoryName);
    }

    private Set<Modifier> getClassModifiers(Element classElement) {
        Set<Modifier> modifiers = new HashSet<>();
        modifiers.add(Modifier.FINAL);
        if (classElement.getModifiers().contains(Modifier.PUBLIC)) {
            modifiers.add(Modifier.PUBLIC);
        }
        return modifiers;
    }

    private String extractPackageName(Element element) {
        return element.getEnclosingElement().toString();
    }

    private LensMeta makeLensMeta(Element classElement, Lens annotation) {
        String path = annotation.path();
        String[] properties = path.split("\\.");
        String lensName = makeLensName(annotation.lensName(), annotation.type(), properties);

        LensMeta meta = new LensMeta(lensName, annotation.type());
        Element currentClassElement = classElement;

        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            Element field = findFieldByName(property, currentClassElement);
            LensPartMeta part = new LensPartMeta(currentClassElement.asType(), field.asType(), property);
            meta.addLensPart(part);

            TypeMirror fieldTypeMirror = field.asType();
            if (fieldTypeMirror.getKind().isPrimitive() && !isLast(i, properties.length)) {
                throw new LensProcessingException(MessageFactory.wrongPlaceOfPrimitiveType(classElement));
            }
            if (fieldTypeMirror.getKind() == TypeKind.DECLARED) {
                currentClassElement = ((DeclaredType) field.asType()).asElement();
            }
        }

        return meta;
    }

    private boolean isLast(int currentIndex, int length) {
        return currentIndex == length - 1;
    }

    private String makeLensName(String userLensName, LensType lensType, String[] properties) {
        if (StringUtils.isNotBlank(userLensName)) {
            return userLensName;
        }
        return String.format("%s_%s_%s", makeLensName(properties), lensType, LENS_NAME_SUFFIX);
    }

    private String makeLensName(String[] properties) {
        return Stream.of(properties)
                .map(it -> CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, it))
                .collect(Collectors.joining("_"));
    }

    private Element findFieldByName(String fieldName, Element classElement) {
        DeclaredType type = (DeclaredType) classElement.asType();

        return elementUtil.getAllMembers((TypeElement) type.asElement()).stream()
                .filter(it -> it.getKind() == ElementKind.FIELD)
                .filter(it -> !it.getModifiers().contains(Modifier.STATIC))
                .filter(it -> it.getSimpleName().toString().equalsIgnoreCase(fieldName))
                .findFirst()
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

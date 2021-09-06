package dev.khbd.lens4j.processor;

import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.JavaFile;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private LensGenerator lensGenerator;
    private Filer filer;
    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.lensGenerator = new LensGenerator();
        this.logger = new Logger(processingEnv.getMessager());
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
            return makeFactoryMetaFromClassElement(element);
        }
        throw new LensProcessingException(Message.of("@GenLenses is not allowed here", element));
    }

    private FactoryMeta makeFactoryMetaFromClassElement(Element classElement) {
        GenLenses annotation = classElement.getAnnotation(GenLenses.class);
        return makeFactoryMetaFromClassElement(classElement, annotation);
    }

    private FactoryMeta makeFactoryMetaFromClassElement(Element classElement, GenLenses annotation) {
        checkLensPath(classElement, List.of(annotation.lenses()));

        FactoryMeta factory = new FactoryMeta(extractPackageName(classElement),
                makeFactoryName(classElement, annotation));

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

    private String makeFactoryName(Element element, GenLenses annotation) {
        String factoryName = annotation.factoryName();
        if (StringUtils.isBlank(factoryName)) {
            return String.format("%s%s", element.getSimpleName(), DEFAULT_FACTORY_NAME);
        }
        return StringUtils.capitalize(factoryName);
    }

    private String extractPackageName(Element element) {
        return element.getEnclosingElement().toString();
    }

    private LensMeta makeLensMeta(Element classElement, Lens annotation) {
        String path = annotation.path();
        String[] properties = path.split("\\.");
        String lensName = makeLensName(annotation.lensName(), properties);

        LensMeta meta = new LensMeta(lensName, annotation.type());
        Element currentClassElement = classElement;

        for (String property : properties) {
            Optional<? extends Element> elementByName = findElementByName(property, currentClassElement);
            if (elementByName.isPresent()) {
                Element field = elementByName.get();
                LensPartMeta part = new LensPartMeta(currentClassElement.asType(), field.asType(), property);
                meta.addLensPart(part);

                currentClassElement = ((DeclaredType) field.asType()).asElement();
            }
        }

        return meta;
    }

    private String makeLensName(String userLensName, String[] properties) {
        if (StringUtils.isNotBlank(userLensName)) {
            return userLensName;
        }
        return Stream.of(properties)
                .map(it -> CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, it))
                .collect(Collectors.joining("_"));
    }

    private Optional<? extends Element> findElementByName(String fieldName, Element element) {
        return element.getEnclosedElements().stream()
                .filter(it -> it.getKind() == ElementKind.FIELD)
                .filter(it -> !it.getModifiers().contains(Modifier.STATIC))
                .filter(it -> it.getSimpleName().toString().equalsIgnoreCase(fieldName))
                .findFirst();
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

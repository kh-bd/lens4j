package com.github.lens.processor;

import com.github.lens.core.annotations.GenLenses;
import com.github.lens.core.annotations.Lens;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
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
                GenLenses annotation = element.getAnnotation(GenLenses.class);
                FactoryMeta factoryMeta = makeFactoryMeta(element, annotation);
                JavaFile file = lensGenerator.generate(factoryMeta);
                writeFile(file);
            }
        } catch (LensProcessingException e) {
            logger.error(e.getError());
            return ProcessResult.ERROR;
        }
        return ProcessResult.GENERATED;
    }

    private FactoryMeta makeFactoryMeta(Element classElement, GenLenses annotation) {
        checkLenses(classElement, List.of(annotation.lenses()));

        FactoryMeta factory = new FactoryMeta(extractPackageName(classElement),
                makeFactoryName(classElement, annotation));

        for (Lens lens : annotation.lenses()) {
            factory.addLens(makeLensMeta(classElement, lens));
        }

        return factory;
    }

    private void checkLenses(Element classElement, List<Lens> lenses) {
        Map<String, List<Lens>> lensNames = lenses.stream().collect(Collectors.groupingBy(Lens::lensName));
        for (Map.Entry<String, List<Lens>> entry : lensNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new LensProcessingException(Message.of("Lens names for type should be unique"));
            }
        }

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
        return element.getEnclosingElement().getSimpleName().toString();
    }

    private LensMeta makeLensMeta(Element classElement, Lens annotation) {
        LensMeta meta = new LensMeta(annotation.lensName(), annotation.type());

        String path = annotation.path();

        String[] properties = path.split("\\.");
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

    private Optional<? extends Element> findElementByName(String fieldName, Element element) {
        return element.getEnclosedElements().stream()
                .filter(it -> it.getKind() == ElementKind.FIELD)
                .filter(it -> !it.getModifiers().contains(Modifier.STATIC))
                .filter(it -> it.getSimpleName().toString().equalsIgnoreCase(fieldName))
                .findFirst();
    }

    private Set<? extends Element> findLensElements(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.class)
                .stream()
                .filter(it -> it.getKind() == ElementKind.CLASS)
                .collect(Collectors.toSet());
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

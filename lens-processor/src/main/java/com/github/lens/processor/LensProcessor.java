package com.github.lens.processor;

import com.github.lens.core.annotations.GenLenses;
import com.github.lens.core.annotations.Lens;
import com.github.lens.processor.generator.FactoryMetadata;
import com.github.lens.processor.generator.GenerationContext;
import com.github.lens.processor.generator.LensGenerator;
import com.github.lens.processor.generator.LensMetadata;
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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
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
            List<FactoryMetadata> factoryMetadata = new ArrayList<>();
            for (Element element : lensElements) {
                GenLenses annotation = element.getAnnotation(GenLenses.class);
                String factoryName = annotation.factoryName();
                List<Lens> lenses = Arrays.asList(annotation.lenses());
                checkLensNames(lenses);
                List<LensMetadata> lensMetadata = makeLensMetadata(element, lenses);
                factoryMetadata.add(makeFactoryMetadata(element, factoryName, lensMetadata));
            }
            factoryMetadata.sort(Comparator.comparing(it -> it.getRootType().toString()));
            List<JavaFile> javaFiles = lensGenerator.generate(GenerationContext.of(factoryMetadata));
            writeFile(javaFiles);
        } catch (Exception e) {
            return ProcessResult.ERROR;
        }
        return ProcessResult.GENERATED;
    }

    private String makeFactoryName(Element element, String factoryName) {
        if (StringUtils.isBlank(factoryName)) {
            return String.format("%s%s", element.getSimpleName(), DEFAULT_FACTORY_NAME);
        }
        return StringUtils.capitalize(factoryName);
    }

    private void writeFile(List<JavaFile> javaFiles) throws IOException {
        for (JavaFile javaFile : javaFiles) {
            javaFile.writeTo(filer);
        }
    }

    private void checkLensNames(List<Lens> lenses) throws Exception {
        Map<String, List<Lens>> lensNames = lenses.stream().collect(Collectors.groupingBy(Lens::lensName));
        for (Map.Entry<String, List<Lens>> entry : lensNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                logger.log(Message.error("Lens names for type should be unique"));
                throw new Exception("Lens names for type should be unique");
            }
        }
    }

    private FactoryMetadata makeFactoryMetadata(Element element, String factoryName,
                                                List<LensMetadata> lensMetadata) {
        return FactoryMetadata.of(
                makeFactoryName(element, factoryName),
                extractPackageName(element),
                element.asType(), lensMetadata);
    }

    private List<LensMetadata> makeLensMetadata(Element element, List<Lens> lenses) {
        return lenses.stream()
                .map(makeLensMetadata(element))
                .collect(Collectors.toList());
    }

    private String extractPackageName(Element element) {
        return element.getEnclosingElement().getSimpleName().toString();
    }

    private Function<Lens, LensMetadata> makeLensMetadata(Element element) {
        return annotation -> {
            Deque<Element> elementQueue = new ArrayDeque<>();
            String path = annotation.path();
            String lensName = annotation.lensName();
            String[] fields = path.split("\\.");
            findAndFillElement(elementQueue, element, fields);
            return LensMetadata.of(lensName, annotation.type(), elementQueue);
        };
    }

    private void findAndFillElement(Queue<Element> elementQueue, Element element, String[] fields) {
        Optional<? extends Element> elementByName = findElementByName(fields[0], element);
        if (elementByName.isPresent()) {
            Element e = elementByName.get();
            elementQueue.add(e);
            String[] newFields;
            if (fields.length > 1) {
                newFields = new String[fields.length - 1];
                System.arraycopy(fields, 1, newFields, 0, fields.length - 1);
            } else {
                newFields = fields;
            }
            findAndFillElement(
                    elementQueue,
                    ((DeclaredType) e.asType()).asElement(),
                    newFields
            );
        }
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

    enum ProcessResult {
        GENERATED,
        ERROR
    }
}

package dev.khbd.lens4j.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.processor.generator.LensFactoryGenerator;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.lens4j.processor.meta.LensMeta;
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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Annotation processor for generating lens.
 *
 * @author Alexey_Bodyak
 */
@AutoService(Processor.class)
public class LensProcessor extends AbstractProcessor {

    private static final String DEFAULT_FACTORY_SUFFIX = "Lenses";

    private LensFactoryGenerator lensGenerator;
    private Filer filer;
    private Logger logger;
    private Elements elementUtil;
    private Types typeUtil;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.lensGenerator = new LensFactoryGenerator(processingEnv.getTypeUtils());
        this.logger = new Logger(processingEnv.getMessager());
        this.elementUtil = processingEnv.getElementUtils();
        this.typeUtil = processingEnv.getTypeUtils();
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
        FactoryMeta factory = new FactoryMeta(getPackage(classElement),
                makeFactoryName(classElement, annotation), getClassModifiers(classElement));

        LensMetaBuilder metaBuilder = new LensMetaBuilder(classElement, typeUtil);
        for (Lens lens : annotation.lenses()) {
            factory.addLens(metaBuilder.build(lens));
        }
        checkLensNames(classElement, factory.getLenses());

        return factory;
    }

    private void checkLensNames(Element classElement, List<LensMeta> lenses) {
        Map<String, List<LensMeta>> lensNames = lenses.stream().collect(Collectors.groupingBy(LensMeta::getName));
        for (Map.Entry<String, List<LensMeta>> entry : lensNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new LensProcessingException(MessageFactory.existNotUniqueLensName(classElement));
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

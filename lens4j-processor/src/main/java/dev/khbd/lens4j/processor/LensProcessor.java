package dev.khbd.lens4j.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.processor.generator.InlinedLensFactoryGenerator;
import dev.khbd.lens4j.processor.generator.LensFactoryGenerator;
import dev.khbd.lens4j.processor.meta.FactoryId;
import dev.khbd.lens4j.processor.meta.FactoryMeta;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
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

    private LensFactoryGenerator lensGenerator;
    private Filer filer;
    private Logger logger;

    private LensFactoryMetaCollector metaCreator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.metaCreator = new LensFactoryMetaCollector(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
        this.logger = new Logger(processingEnv.getMessager());
        this.lensGenerator = new InlinedLensFactoryGenerator(processingEnv.getTypeUtils());
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Options.getOptionsKeys();
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
            Map<FactoryId, List<FactoryMeta>> groupedFactories =
                    lensElements.stream()
                            .map(metaCreator::collect)
                            .collect(Collectors.groupingBy(FactoryMeta::getId));
            for (List<FactoryMeta> factories : groupedFactories.values()) {
                FactoryMeta merged = mergeFactories(factories);
                JavaFile file = lensGenerator.generate(merged);
                writeFile(file);
            }
        } catch (LensProcessingException e) {
            logger.error(e.getError());
            return ProcessResult.ERROR;
        }
        return ProcessResult.GENERATED;
    }

    private FactoryMeta mergeFactories(List<FactoryMeta> factories) {
        if (factories.size() == 1) {
            return factories.get(0);
        }
        FactoryMeta merged = factories.get(0);
        for(int i = 1; i < factories.size(); i++) {
            FactoryMeta current = factories.get(i);
            if (!merged.canBeMergedWith(current)) {
                throw new LensProcessingException(MessageFactory.factoriesCannotBeMerged(current.getId()));
            }
            merged = merged.merge(current);
        }
        return merged;
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

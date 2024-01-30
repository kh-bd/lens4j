package dev.khbd.lens4j.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.processor.generator.InlinedLensFactoryGenerator;
import dev.khbd.lens4j.processor.generator.LensFactoryGenerator;
import dev.khbd.lens4j.processor.meta.FactoryMeta;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
    private ElementAndGenLensesSearcher searcher;
    private LensFactoryMetaCollector metaCollector;
    private FactoryMetaMerger merger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.metaCollector = new LensFactoryMetaCollector(processingEnv.getTypeUtils(), processingEnv.getElementUtils());
        this.logger = new Logger(processingEnv.getMessager());
        this.lensGenerator = new InlinedLensFactoryGenerator(processingEnv.getTypeUtils());
        this.searcher = new ElementAndGenLensesSearcher(processingEnv.getTypeUtils());
        this.merger = new FactoryMetaMerger();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Options.getOptionsKeys();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                GenLenses.class.getCanonicalName(),
                GenLenses.GenLensesMulti.class.getCanonicalName()
        );
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
        try {
            Set<ElementAndGenLenses> lensElements = searcher.search(roundEnv);
            List<FactoryMeta> factories = lensElements.stream()
                    .map(elementAndGenLenses -> metaCollector.collect(elementAndGenLenses.getRoot(), elementAndGenLenses.getAnnotation()))
                    .collect(Collectors.toList());
            List<FactoryMeta> merged = merger.merge(factories);
            for (FactoryMeta factoryMeta : merged) {
                JavaFile file = lensGenerator.generate(factoryMeta);
                writeFile(file);
            }
        } catch (LensProcessingException e) {
            logger.error(e.getError());
            return ProcessResult.ERROR;
        }
        return ProcessResult.GENERATED;
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

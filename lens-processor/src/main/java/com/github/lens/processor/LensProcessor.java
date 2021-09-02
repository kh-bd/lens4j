/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor;

import com.github.lens.core.annotations.GenReadLens;
import com.github.lens.core.annotations.GenReadLenses;
import com.github.lens.core.annotations.GenReadWriteLens;
import com.github.lens.core.annotations.GenReadWriteLenses;
import com.github.lens.processor.generator.BaseLensGenerator;
import com.github.lens.processor.generator.ElementMetadata;
import com.github.lens.processor.generator.FactoryMetadata;
import com.github.lens.processor.generator.GenerationContext;
import com.github.lens.processor.generator.LensGenerator;
import com.github.lens.processor.generator.LensMetadata;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import org.apache.commons.lang3.tuple.Pair;

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
import javax.tools.Diagnostic;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Annotation processor for generating lens.
 *
 * @author Alexey_Bodyak
 */
@AutoService(Processor.class)
public class LensProcessor extends AbstractProcessor {

    private LensGenerator lensGenerator;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.filer = processingEnv.getFiler();
        this.lensGenerator = new BaseLensGenerator();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.<String>builder()
                .add(GenReadLens.class.getName())
                .add(GenReadLenses.class.getName())
                .add(GenReadWriteLens.class.getName())
                .add(GenReadWriteLenses.class.getName())
                .build();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processReadElements(roundEnv) == ProcessResult.ERROR;
    }

    private ProcessResult processReadElements(RoundEnvironment roundEnv) {
        Set<? extends Element> lensElements = findLensElements(roundEnv);

        List<Pair<String, ElementMetadata>> factoryMetadata = new ArrayList<>();
        for (Element element : lensElements) {
            try {
                if (hasReadAnnotation(element)) {
                    factoryMetadata.add(processReadElements(element));
                }
                if (hasReadWriteAnnotation(element)) {
                    factoryMetadata.add(processReadWriteElements(element));
                }
            } catch (Exception e) {
                return ProcessResult.ERROR;
            }
        }
        factoryMetadata.sort(Comparator.comparing(it -> it.getRight().getElement().getSimpleName().toString()));
        GenerationContext context = GenerationContext.of(makeFactoryMetadata(factoryMetadata));
        List<JavaFile> javaFiles = lensGenerator.generate(context);
        writeFile(javaFiles);
        return ProcessResult.GENERATED;
    }

    private boolean hasReadAnnotation(Element element) {
        return Objects.nonNull(element.getAnnotation(GenReadLens.class))
                || Objects.nonNull(element.getAnnotation(GenReadLenses.class));
    }

    private boolean hasReadWriteAnnotation(Element element) {
        return Objects.nonNull(element.getAnnotation(GenReadWriteLens.class))
                || Objects.nonNull(element.getAnnotation(GenReadWriteLenses.class));
    }

    private Pair<String, ElementMetadata> processReadElements(Element element) {
        List<GenReadLens> annotations = getReadAnnotationsFromElement(element);
        List<String> factoryNames = extractReadFactoryNames(annotations);
        if (factoryNames.size() > 1) {
            error(element, "Lens from one type should be have one factory");
            throw new RuntimeException("Lens from one type should be have one factory");
        }

        List<LensMetadata> lensMetadataList = makeReadLensMetadata(element, annotations);
        return Pair.of(factoryNames.get(0), ElementMetadata.of(element, lensMetadataList));
    }

    private Pair<String, ElementMetadata> processReadWriteElements(Element element) {
        List<GenReadWriteLens> annotations = getReadWriteAnnotationsFromElement(element);
        List<String> factoryNames = extractReadWriteFactoryNames(annotations);
        if (factoryNames.size() > 1) {
            error(element, "Lens from one type should be have one factory");
            throw new RuntimeException("Lens from one type should be have one factory");
        }

        List<LensMetadata> lensMetadataList = makeReadWriteLensMetadata(element, annotations);
        return Pair.of(factoryNames.get(0), ElementMetadata.of(element, lensMetadataList));
    }

    private void writeFile(List<JavaFile> javaFiles) {
        try {
            for (JavaFile javaFile : javaFiles) {
                javaFile.writeTo(filer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<LensMetadata> makeReadLensMetadata(Element element, List<GenReadLens> annotations) {
        return annotations.stream()
                .map(makeReadLensMeta(element))
                .collect(Collectors.toList());
    }

    private List<LensMetadata> makeReadWriteLensMetadata(Element element, List<GenReadWriteLens> annotations) {
        return annotations.stream()
                .map(makeReadWriteLensMeta(element))
                .collect(Collectors.toList());
    }

    private List<GenReadLens> getReadAnnotationsFromElement(Element element) {
        return Optional.ofNullable(element.getAnnotation(GenReadLenses.class))
                .map(it -> Arrays.asList(it.value()))
                .orElseGet(() -> Collections.singletonList(element.getAnnotation(GenReadLens.class)));
    }

    private List<GenReadWriteLens> getReadWriteAnnotationsFromElement(Element element) {
        return Optional.ofNullable(element.getAnnotation(GenReadWriteLenses.class))
                .map(it -> Arrays.asList(it.value()))
                .orElseGet(() -> Collections.singletonList(element.getAnnotation(GenReadWriteLens.class)));
    }

    private List<String> extractReadFactoryNames(List<GenReadLens> annotations) {
        return annotations.stream()
                .map(GenReadLens::factoryName).distinct()
                .collect(Collectors.toList());
    }

    private List<String> extractReadWriteFactoryNames(List<GenReadWriteLens> annotations) {
        return annotations.stream()
                .map(GenReadWriteLens::factoryName).distinct()
                .collect(Collectors.toList());
    }

    private List<FactoryMetadata> makeFactoryMetadata(List<Pair<String, ElementMetadata>> list) {
        Map<String, List<ElementMetadata>> data = list.stream().collect(Collectors.groupingBy(Pair::getLeft,
                Collectors.mapping(Pair::getRight, Collectors.toList())));

        return data.entrySet().stream()
                .map(it -> FactoryMetadata.of(it.getKey(), it.getValue()))
                .collect(Collectors.toList());
    }

    private Function<GenReadLens, LensMetadata> makeReadLensMeta(Element element) {
        return annotation -> {
            Deque<Element> elementQueue = new ArrayDeque<>();
            String path = annotation.path();
            String lensName = annotation.lensName();
            String[] fields = path.split("\\.");
            findAndFillElement(elementQueue, element, fields);
            return LensMetadata.of(lensName, true, elementQueue);
        };
    }

    private Function<GenReadWriteLens, LensMetadata> makeReadWriteLensMeta(Element element) {
        return annotation -> {
            Deque<Element> elementQueue = new ArrayDeque<>();
            String path = annotation.path();
            String lensName = annotation.lensName();
            String[] fields = path.split("\\.");
            findAndFillElement(elementQueue, element, fields);
            return LensMetadata.of(lensName, false, elementQueue);
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
        return Stream.concat(
                Stream.concat(
                        roundEnv.getElementsAnnotatedWith(GenReadLens.class).stream(),
                        roundEnv.getElementsAnnotatedWith(GenReadLenses.class).stream()
                ),
                Stream.concat(
                        roundEnv.getElementsAnnotatedWith(GenReadWriteLens.class).stream(),
                        roundEnv.getElementsAnnotatedWith(GenReadWriteLenses.class).stream()
                )
        )
                .filter(it -> it.getKind() == ElementKind.CLASS)
                .collect(Collectors.toSet());
    }

    private void error(Element element, String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    enum ProcessResult {
        GENERATED,
        ERROR
    }
}

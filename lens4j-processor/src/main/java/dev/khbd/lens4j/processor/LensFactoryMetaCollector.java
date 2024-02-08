package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.processor.meta.FactoryId;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.lens4j.processor.meta.LensMeta;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Lens factory metadata builder.
 *
 * @author Sergei_Khadanovich
 */
@RequiredArgsConstructor
public class LensFactoryMetaCollector {

    private static final String DEFAULT_FACTORY_SUFFIX = "Lenses";

    private final Types typeUtil;
    private final Elements elementUtil;

    /**
     * Build factory metadata by annotated element.
     *
     * @param annotated annotated element
     * @param root      root element
     * @return built factory metadata
     */
    public FactoryMeta collect(Element annotated, TypeElement root, GenLenses annotation) {
        verifyClass(root);

        FactoryId factoryId = makeFactoryId(annotated, root, annotation);

        FactoryMeta.FactoryMetaBuilder factoryBuilder =
                FactoryMeta.builder()
                        .id(factoryId)
                        .modifiers(getClassModifiers(annotated, annotation));

        LensMetaCollector creator = new LensMetaCollector(root, typeUtil);
        for (Lens lens : annotation.lenses()) {
            factoryBuilder.lens(creator.collect(factoryId, lens));
        }

        FactoryMeta factory = factoryBuilder.build();

        checkLensNames(root, factory.getLenses());

        return factory;
    }

    private void verifyClass(TypeElement classElement) {
        if (!classElement.getTypeParameters().isEmpty()) {
            throw new LensProcessingException(MessageFactory.genLensNotAllowedOnGenericClasses(classElement));
        }
    }

    private FactoryId makeFactoryId(Element annotated, TypeElement root, GenLenses annotation) {
        return new FactoryId(getPackage(annotated), makeFactoryName(root, annotation));
    }

    private String makeFactoryName(TypeElement root, GenLenses annotation) {
        String factoryName = annotation.factoryName();
        if (StringUtils.isBlank(factoryName)) {
            return deriveFactoryName(root);
        }
        return StringUtils.capitalize(factoryName);
    }

    private String deriveFactoryName(TypeElement root) {
        String joinedClassNames = ProcessorUtils.getNestedHierarchy(root).stream()
                .map(Element::getSimpleName)
                .collect(Collectors.joining());
        return joinedClassNames + DEFAULT_FACTORY_SUFFIX;
    }


    private String getPackage(Element classElement) {
        return elementUtil.getPackageOf(classElement).toString();
    }

    private Set<Modifier> getClassModifiers(Element annotated, GenLenses annotation) {
        Set<Modifier> modifiers = new HashSet<>();
        modifiers.add(Modifier.FINAL);

        switch (annotation.accessLevel()) {
            case PUBLIC:
                modifiers.add(Modifier.PUBLIC);
                break;
            case PACKAGE:
                break;
            case INHERIT:
                if (annotated instanceof TypeElement typed) {
                    TypeElement top = ProcessorUtils.getTopLevelClass(typed);
                    if (top.getModifiers().contains(Modifier.PUBLIC)) {
                        modifiers.add(Modifier.PUBLIC);
                    }
                }
                break;
            default:
                throw new IllegalStateException(annotation.accessLevel().name() + " is unknown GenLenses#AccessLevel.");
        }

        return modifiers;
    }

    private void checkLensNames(Element root, List<LensMeta> lenses) {
        Map<String, List<LensMeta>> lensNames = lenses.stream().collect(Collectors.groupingBy(LensMeta::getName));
        for (Map.Entry<String, List<LensMeta>> entry : lensNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new LensProcessingException(MessageFactory.existNotUniqueLensName(root));
            }
        }
    }

}

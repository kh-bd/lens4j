package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.lens4j.processor.meta.LensMeta;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
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
 * @author Sergei_Khadanovich
 */
public class LensFactoryMetaBuilder {

    private static final String DEFAULT_FACTORY_SUFFIX = "Lenses";

    private final Types typeUtil;
    private final Elements elementUtil;

    public LensFactoryMetaBuilder(Types typeUtil, Elements elementUtil) {
        this.typeUtil = typeUtil;
        this.elementUtil = elementUtil;
    }

    /**
     * Build factory metadata by annotated element.
     *
     * @param element element
     * @return built factory metadata
     */
    public FactoryMeta build(Element element) {
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
                makeFactoryName(classElement, annotation), getClassModifiers(classElement, annotation));

        LensMetaBuilder metaBuilder = new LensMetaBuilder(classElement, typeUtil);
        for (Lens lens : annotation.lenses()) {
            factory.addLens(metaBuilder.build(lens));
        }
        checkLensNames(classElement, factory.getLenses());

        return factory;
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


    private String getPackage(TypeElement classElement) {
        return elementUtil.getPackageOf(classElement).toString();
    }

    private Set<Modifier> getClassModifiers(TypeElement classElement, GenLenses annotation) {
        Set<Modifier> modifiers = new HashSet<>();
        modifiers.add(Modifier.FINAL);

        switch (annotation.accessLevel()) {
            case PUBLIC:
                modifiers.add(Modifier.PUBLIC);
                break;
            case INHERIT:
                TypeElement topLevelClass = ProcessorUtils.getTopLevelClass(classElement);
                if (topLevelClass.getModifiers().contains(Modifier.PUBLIC)) {
                    modifiers.add(Modifier.PUBLIC);
                }
                break;
            case PACKAGE:
                break;
            default:
                throw new IllegalStateException(annotation.accessLevel().name() + " is unknown GenLenses#AccessLevel.");
        }

        return modifiers;
    }

    private void checkLensNames(Element classElement, List<LensMeta> lenses) {
        Map<String, List<LensMeta>> lensNames = lenses.stream().collect(Collectors.groupingBy(LensMeta::getName));
        for (Map.Entry<String, List<LensMeta>> entry : lensNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new LensProcessingException(MessageFactory.existNotUniqueLensName(classElement));
            }
        }
    }

}

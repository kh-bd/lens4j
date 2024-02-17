package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;
import lombok.Value;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Element and annotation pair.
 *
 * @author Sergei Khadanovich
 */
@Value
class AnnotatedElement {

    Element annotated;
    TypeElement root;
    GenLenses annotation;
}

package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Element and annotation pair.
 *
 * @author Sergei Khadanovich
 */
record AnnotatedElement(Element annotated, TypeElement root, GenLenses annotation) {
}

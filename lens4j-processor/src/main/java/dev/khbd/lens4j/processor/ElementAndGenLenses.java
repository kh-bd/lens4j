package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;

import javax.lang.model.element.Element;

/**
 * Element and annotation pair.
 *
 * @author Sergei Khadanovich
 */
record ElementAndGenLenses(Element element, GenLenses annotation) {
}

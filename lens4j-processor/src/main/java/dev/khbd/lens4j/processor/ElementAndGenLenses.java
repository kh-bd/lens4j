package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;
import lombok.Value;

import javax.lang.model.element.Element;

/**
 * Element and annotation pair.
 *
 * @author Sergei Khadanovich
 */
@Value
class ElementAndGenLenses {
    Element element;
    GenLenses annotation;
}

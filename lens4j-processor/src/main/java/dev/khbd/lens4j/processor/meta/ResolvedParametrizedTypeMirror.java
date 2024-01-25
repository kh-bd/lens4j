package dev.khbd.lens4j.processor.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

/**
 * Parametrized type mirror with resolved type arguments.
 *
 * @author Sergei_Khadanovich
 */
@Getter
@RequiredArgsConstructor
public class ResolvedParametrizedTypeMirror {

    private final TypeMirror typeMirror;
    private final List<ResolvedParametrizedTypeMirror> actualTypeArguments = new ArrayList<>();

    /**
     * Append actual type argument to arguments list.
     *
     * @param actualTypeArgument actual type argument
     * @return self for chaining
     */
    public ResolvedParametrizedTypeMirror withActualTypeArgument(ResolvedParametrizedTypeMirror actualTypeArgument) {
        this.actualTypeArguments.add(actualTypeArgument);
        return this;
    }

    public boolean isParametrized() {
        return !actualTypeArguments.isEmpty();
    }

    public ResolvedParametrizedTypeMirror getFirstActualTypeArgument() {
        if (actualTypeArguments.isEmpty()) {
            throw new IndexOutOfBoundsException("There is no any actual type argument");
        }
        return actualTypeArguments.get(0);
    }
}

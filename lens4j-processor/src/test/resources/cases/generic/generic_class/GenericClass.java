package cases.generic.generic_class;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "value"))
public class GenericClass<T> {
    T value;

    public T getValue() {
        return value;
    }
}

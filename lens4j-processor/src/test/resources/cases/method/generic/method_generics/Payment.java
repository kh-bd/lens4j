package cases.method.generic.method_generics;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "from()"))
public class Payment {

    public <U> U from() {
        return null;
    }
}

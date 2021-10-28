package cases.method.generic.class_generics;

import java.util.List;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(
        lenses = {
                @Lens(path = "from()"),
                @Lens(path = "froms()")
        }
)
public class Payment extends AbstractPayment<String> {
}

class AbstractPayment<F> {
    F from;
    List<F> froms;

    F from() {
        return from;
    }

    List<F> froms() {
        return froms;
    }
}

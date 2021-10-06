package cases.generic.projection;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "from"),
        @Lens(path = "from", type = LensType.READ_WRITE),
        @Lens(path = "from.value"),
        @Lens(path = "from.value", type = LensType.READ_WRITE)
})
public class Payment extends AbstractPayment<String, StrFrom> {
}

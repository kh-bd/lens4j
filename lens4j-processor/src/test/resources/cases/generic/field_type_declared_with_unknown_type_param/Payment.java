package cases.generic.field_type_declared_with_unknown_type_param;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "boxed"),
        @Lens(path = "boxed", type = LensType.READ_WRITE),
        @Lens(path = "boxed.value"),
        @Lens(path = "boxed.value", type = LensType.READ_WRITE),
        @Lens(path = "boxed.value.code"),
        @Lens(path = "boxed.value.code", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxed"),
        @Lens(path = "boxedBoxed", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxed.value"),
        @Lens(path = "boxedBoxed.value", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxed.value.value"),
        @Lens(path = "boxedBoxed.value.value", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxed.value.value.code"),
        @Lens(path = "boxedBoxed.value.value.code", type = LensType.READ_WRITE)
})
public class Payment extends AbstractPayment<Currency> {
}
package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "from"),
        @Lens(path = "from", type = LensType.READ_WRITE),
        @Lens(path = "to"),
        @Lens(path = "to", type = LensType.READ_WRITE),
        @Lens(path = "to.value"),
        @Lens(path = "to.value", type = LensType.READ_WRITE)
})
class StrPayment extends BoxedToPayment<String> {
}

@GenLenses(lenses = {
        @Lens(path = "from"),
        @Lens(path = "from", type = LensType.READ_WRITE),
        @Lens(path = "to"),
        @Lens(path = "to", type = LensType.READ_WRITE),
        @Lens(path = "to.value"),
        @Lens(path = "to.value", type = LensType.READ_WRITE)
})
class ArrayPayment extends BoxedToPayment<int[]> {
}

@GenLenses(lenses = {
        @Lens(path = "from"),
        @Lens(path = "from", type = LensType.READ_WRITE),
        @Lens(path = "to"),
        @Lens(path = "to", type = LensType.READ_WRITE),
        @Lens(path = "to.value"),
        @Lens(path = "to.value", type = LensType.READ_WRITE),
        @Lens(path = "to.value.value"),
        @Lens(path = "to.value.value", type = LensType.READ_WRITE),
})
class GenPayment extends BoxedToPayment<Box<String>> {
}

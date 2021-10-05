package cases.generic.type_resolved_to_generic_based_type;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = {
        @Lens(path = "left.value.code"),
        @Lens(path = "right.value.code")
})
class CurrencyPair extends Pair<BoxedCurrency, Box<Currency>> {
}
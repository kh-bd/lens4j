package cases.generic.field_type_declared_with_known_type_param;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = {
        @Lens(path = "boxedCurrency"),
        @Lens(path = "boxedCurrency.value"),
        @Lens(path = "boxedCurrency.value.code")
})
public class Payment {

    private Box<Currency> boxedCurrency;

    public Box<Currency> getBoxedCurrency() {
        return boxedCurrency;
    }
}
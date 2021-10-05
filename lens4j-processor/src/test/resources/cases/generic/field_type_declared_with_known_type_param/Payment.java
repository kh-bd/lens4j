package cases.generic.field_type_declared_with_known_type_param;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "boxedCurrency"),
        @Lens(path = "boxedCurrency", type = LensType.READ_WRITE),
        @Lens(path = "boxedCurrency.value"),
        @Lens(path = "boxedCurrency.value", type = LensType.READ_WRITE),
        @Lens(path = "boxedCurrency.value.code"),
        @Lens(path = "boxedCurrency.value.code", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxedCurrency"),
        @Lens(path = "boxedBoxedCurrency", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxedCurrency.value"),
        @Lens(path = "boxedBoxedCurrency.value", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxedCurrency.value.value"),
        @Lens(path = "boxedBoxedCurrency.value.value", type = LensType.READ_WRITE),
        @Lens(path = "boxedBoxedCurrency.value.value.code"),
        @Lens(path = "boxedBoxedCurrency.value.value.code", type = LensType.READ_WRITE)
})
public class Payment {

    private Box<Currency> boxedCurrency;
    private Box<Box<Currency>> boxedBoxedCurrency;

    public Box<Currency> getBoxedCurrency() {
        return boxedCurrency;
    }

    public Box<Box<Currency>> getBoxedBoxedCurrency() {
        return boxedBoxedCurrency;
    }

    public void setBoxedCurrency(Box<Currency> boxedCurrency) {
        this.boxedCurrency = boxedCurrency;
    }

    public void setBoxedBoxedCurrency(Box<Box<Currency>> boxedBoxedCurrency) {
        this.boxedBoxedCurrency = boxedBoxedCurrency;
    }
}
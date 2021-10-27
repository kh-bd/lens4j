package cases.method.wrong_position.non_single_part_path;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = @Lens(path = "currency.getCode()", type = LensType.READ_WRITE))
public class Payment {
    private Currency currency;

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}
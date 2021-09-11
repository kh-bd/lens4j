package util.examples;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "currency.bid"))
class FieldNotFound {
    Currency currency;

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    Currency getCurrency() {
        return currency;
    }
}

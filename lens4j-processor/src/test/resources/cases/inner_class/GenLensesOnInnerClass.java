package cases.inner_class;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

class GenLensesOnInnerClass {

    @GenLenses(lenses = @Lens(path = "currency.code"))
    class InnerClass {
        Currency currency;

        public void setCurrency(Currency currency) {
            this.currency = currency;
        }

        Currency getCurrency() {
            return currency;
        }
    }
}

package cases.inner_class;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

class Outer {

    public class Inner1 {

        @GenLenses(lenses = @Lens(path = "currency.code"))
        class Inner2 {
            private Currency currency;

            public Currency getCurrency() {
                return currency;
            }

            public void setCurrency(Currency currency) {
                this.currency = currency;
            }
        }
    }
}

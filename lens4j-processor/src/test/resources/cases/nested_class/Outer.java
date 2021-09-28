package cases.nested_class;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

public class Outer {

    public static class Inner1 {

        @GenLenses(lenses = @Lens(path = "currency.code"))
        public static class Inner2 {
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

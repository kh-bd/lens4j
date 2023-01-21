package cases.nested_class;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class OuterInner1Inner2Lenses {
    public static final ReadLens<Outer.Inner1.Inner2, String> CURRENCY_CODE_READ_LENS = new ReadLens<Outer.Inner1.Inner2, String>() {
        @Override
        public final String get(Outer.Inner1.Inner2 object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }
    };

    private OuterInner1Inner2Lenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

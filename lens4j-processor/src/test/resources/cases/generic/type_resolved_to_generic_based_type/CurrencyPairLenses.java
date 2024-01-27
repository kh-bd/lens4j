package cases.generic.type_resolved_to_generic_based_type;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class CurrencyPairLenses {
    public static final ReadLens<CurrencyPair, String> LEFT_VALUE_CODE_READ_LENS = new ReadLens<CurrencyPair, String>() {
        @Override
        public final String get(CurrencyPair object) {
            if (object == null) {
                return null;
            }
            BoxedCurrency left = object.getLeft();
            if (left == null) {
                return null;
            }
            Currency value = left.getValue();
            if (value == null) {
                return null;
            }
            return value.getCode();
        }
    };

    public static final ReadLens<CurrencyPair, String> RIGHT_VALUE_CODE_READ_LENS = new ReadLens<CurrencyPair, String>() {
        @Override
        public final String get(CurrencyPair object) {
            if (object == null) {
                return null;
            }
            Box<Currency> right = object.getRight();
            if (right == null) {
                return null;
            }
            Currency value = right.getValue();
            if (value == null) {
                return null;
            }
            return value.getCode();
        }
    };

    private CurrencyPairLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

package cases.generic.type_resolved_to_generic_based_type;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class CurrencyPairLenses {
    public static final ReadLens<CurrencyPair, String> LEFT_VALUE_CODE_READ_LENS = Lenses.readLens(CurrencyPair::getLeft).andThen(Lenses.readLens(BoxedCurrency::getValue)).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadLens<CurrencyPair, String> RIGHT_VALUE_CODE_READ_LENS = Lenses.readLens(CurrencyPair::getRight).andThen(Lenses.readLens(Box<Currency>::getValue)).andThen(Lenses.readLens(Currency::getCode));

    private CurrencyPairLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

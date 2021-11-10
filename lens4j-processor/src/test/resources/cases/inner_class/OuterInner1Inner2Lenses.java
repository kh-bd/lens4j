package cases.inner_class;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class OuterInner1Inner2Lenses {
    public static final ReadLens<Outer.Inner1.Inner2, String> CURRENCY_CODE_READ_LENS = Lenses.readLens(Outer.Inner1.Inner2::getCurrency).andThen(Lenses.readLens(Currency::getCode));

    private OuterInner1Inner2Lenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

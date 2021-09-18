package cases.sub_class;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class SubClassLenses {
    public static final ReadLens<SubClass, String> CURRENCY_CODE_READ_LENS = Lenses.readLens(SubClass::getCurrency).andThen(Lenses.readLens(Currency::getCode));

    private SubClassLenses() {
    }
}

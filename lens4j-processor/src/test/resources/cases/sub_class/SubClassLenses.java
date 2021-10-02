package cases.sub_class;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class SubClassLenses {
    public static final ReadLens<SubClass, String> PUBLIC_CURRENCY_CODE_READ_LENS = Lenses.readLens(SubClass::getPublicCurrency).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadLens<SubClass, String> PACKAGE_CURRENCY_CODE_READ_LENS = Lenses.readLens(SubClass::getPackageCurrency).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadLens<SubClass, String> PROTECTED_CURRENCY_CODE_READ_LENS = Lenses.readLens(SubClass::getProtectedCurrency).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadLens<SubClass, String> PRIVATE_CURRENCY_CODE_READ_LENS = Lenses.readLens(SubClass::getPrivateCurrency).andThen(Lenses.readLens(Currency::getCode));

    private SubClassLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

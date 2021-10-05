package cases.generic.field_type_declared_with_known_type_param;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Box<Currency>> BOXED_CURRENCY_READ_LENS = Lenses.readLens(Payment::getBoxedCurrency);

    public static final ReadLens<Payment, Currency> BOXED_CURRENCY_VALUE_READ_LENS = Lenses.readLens(Payment::getBoxedCurrency).andThen(Lenses.readLens(Box<Currency>::getValue));

    public static final ReadLens<Payment, String> BOXED_CURRENCY_VALUE_CODE_READ_LENS = Lenses.readLens(Payment::getBoxedCurrency).andThen(Lenses.readLens(Box<Currency>::getValue)).andThen(Lenses.readLens(Currency::getCode));

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

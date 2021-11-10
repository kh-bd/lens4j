package cases.generic.field_type_declared_with_unknown_type_param;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Box<Currency>> BOXED_READ_LENS = Lenses.readLens(Payment::getBoxed);

    public static final ReadWriteLens<Payment, Box<Currency>> BOXED_READ_WRITE_LENS = Lenses.readWriteLens(Payment::getBoxed, Payment::setBoxed);

    public static final ReadLens<Payment, Currency> BOXED_VALUE_READ_LENS = Lenses.readLens(Payment::getBoxed).andThen(Lenses.readLens(Box<Currency>::getValue));

    public static final ReadWriteLens<Payment, Currency> BOXED_VALUE_READ_WRITE_LENS = Lenses.readLens(Payment::getBoxed).andThen(Lenses.readWriteLens(Box<Currency>::getValue, Box<Currency>::setValue));

    public static final ReadLens<Payment, String> BOXED_VALUE_CODE_READ_LENS = Lenses.readLens(Payment::getBoxed).andThen(Lenses.readLens(Box<Currency>::getValue)).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<Payment, String> BOXED_VALUE_CODE_READ_WRITE_LENS = Lenses.readLens(Payment::getBoxed).andThen(Lenses.readLens(Box<Currency>::getValue)).andThen(Lenses.readWriteLens(Currency::getCode, Currency::setCode));

    public static final ReadLens<Payment, Box<Box<Currency>>> BOXED_BOXED_READ_LENS = Lenses.readLens(Payment::getBoxedBoxed);

    public static final ReadWriteLens<Payment, Box<Box<Currency>>> BOXED_BOXED_READ_WRITE_LENS = Lenses.readWriteLens(Payment::getBoxedBoxed, Payment::setBoxedBoxed);

    public static final ReadLens<Payment, Box<Currency>> BOXED_BOXED_VALUE_READ_LENS = Lenses.readLens(Payment::getBoxedBoxed).andThen(Lenses.readLens(Box<Box<Currency>>::getValue));

    public static final ReadWriteLens<Payment, Box<Currency>> BOXED_BOXED_VALUE_READ_WRITE_LENS = Lenses.readLens(Payment::getBoxedBoxed).andThen(Lenses.readWriteLens(Box<Box<Currency>>::getValue, Box<Box<Currency>>::setValue));

    public static final ReadLens<Payment, Currency> BOXED_BOXED_VALUE_VALUE_READ_LENS = Lenses.readLens(Payment::getBoxedBoxed).andThen(Lenses.readLens(Box<Box<Currency>>::getValue)).andThen(Lenses.readLens(Box<Currency>::getValue));

    public static final ReadWriteLens<Payment, Currency> BOXED_BOXED_VALUE_VALUE_READ_WRITE_LENS = Lenses.readLens(Payment::getBoxedBoxed).andThen(Lenses.readLens(Box<Box<Currency>>::getValue)).andThen(Lenses.readWriteLens(Box<Currency>::getValue, Box<Currency>::setValue));

    public static final ReadLens<Payment, String> BOXED_BOXED_VALUE_VALUE_CODE_READ_LENS = Lenses.readLens(Payment::getBoxedBoxed).andThen(Lenses.readLens(Box<Box<Currency>>::getValue)).andThen(Lenses.readLens(Box<Currency>::getValue)).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<Payment, String> BOXED_BOXED_VALUE_VALUE_CODE_READ_WRITE_LENS = Lenses.readLens(Payment::getBoxedBoxed).andThen(Lenses.readLens(Box<Box<Currency>>::getValue)).andThen(Lenses.readLens(Box<Currency>::getValue)).andThen(Lenses.readWriteLens(Currency::getCode, Currency::setCode));

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

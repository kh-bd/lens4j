package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class StrPaymentLenses {
    public static final ReadLens<StrPayment, String> FROM_READ_LENS = Lenses.readLens(StrPayment::getFrom);

    public static final ReadWriteLens<StrPayment, String> FROM_READ_WRITE_LENS = Lenses.readWriteLens(StrPayment::getFrom, StrPayment::setFrom);

    public static final ReadLens<StrPayment, Box<String>> TO_READ_LENS = Lenses.readLens(StrPayment::getTo);

    public static final ReadWriteLens<StrPayment, Box<String>> TO_READ_WRITE_LENS = Lenses.readWriteLens(StrPayment::getTo, StrPayment::setTo);

    public static final ReadLens<StrPayment, String> TO_VALUE_READ_LENS = Lenses.readLens(StrPayment::getTo).andThen(Lenses.readLens(Box<String>::getValue));

    public static final ReadWriteLens<StrPayment, String> TO_VALUE_READ_WRITE_LENS = Lenses.readLens(StrPayment::getTo).andThen(Lenses.readWriteLens(Box<String>::getValue, Box<String>::setValue));

    private StrPaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

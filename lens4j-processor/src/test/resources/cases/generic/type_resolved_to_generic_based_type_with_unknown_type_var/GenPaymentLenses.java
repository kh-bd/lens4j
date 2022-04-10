package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class GenPaymentLenses {
    public static final ReadLens<GenPayment, Box<String>> FROM_READ_LENS = Lenses.readLens(GenPayment::getFrom);

    public static final ReadWriteLens<GenPayment, Box<String>> FROM_READ_WRITE_LENS = Lenses.readWriteLens(GenPayment::getFrom, GenPayment::setFrom);

    public static final ReadLens<GenPayment, Box<Box<String>>> TO_READ_LENS = Lenses.readLens(GenPayment::getTo);

    public static final ReadWriteLens<GenPayment, Box<Box<String>>> TO_READ_WRITE_LENS = Lenses.readWriteLens(GenPayment::getTo, GenPayment::setTo);

    public static final ReadLens<GenPayment, Box<String>> TO_VALUE_READ_LENS = Lenses.readLens(GenPayment::getTo).andThen(Lenses.readLens(Box<Box<String>>::getValue));

    public static final ReadWriteLens<GenPayment, Box<String>> TO_VALUE_READ_WRITE_LENS = Lenses.readLens(GenPayment::getTo).andThen(Lenses.readWriteLens(Box<Box<String>>::getValue, Box<Box<String>>::setValue));

    public static final ReadLens<GenPayment, String> TO_VALUE_VALUE_READ_LENS = Lenses.readLens(GenPayment::getTo).andThen(Lenses.readLens(Box<Box<String>>::getValue)).andThen(Lenses.readLens(Box<String>::getValue));

    public static final ReadWriteLens<GenPayment, String> TO_VALUE_VALUE_READ_WRITE_LENS = Lenses.readLens(GenPayment::getTo).andThen(Lenses.readLens(Box<Box<String>>::getValue)).andThen(Lenses.readWriteLens(Box<String>::getValue, Box<String>::setValue));

    private GenPaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

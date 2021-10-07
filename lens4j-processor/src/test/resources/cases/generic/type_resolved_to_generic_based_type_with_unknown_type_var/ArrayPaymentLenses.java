package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class ArrayPaymentLenses {
    public static final ReadLens<ArrayPayment, int[]> FROM_READ_LENS = Lenses.readLens(ArrayPayment::getFrom);

    public static final ReadWriteLens<ArrayPayment, int[]> FROM_READ_WRITE_LENS = Lenses.readWriteLens(ArrayPayment::getFrom, ArrayPayment::setFrom);

    public static final ReadLens<ArrayPayment, Box<int[]>> TO_READ_LENS = Lenses.readLens(ArrayPayment::getTo);

    public static final ReadWriteLens<ArrayPayment, Box<int[]>> TO_READ_WRITE_LENS = Lenses.readWriteLens(ArrayPayment::getTo, ArrayPayment::setTo);

    public static final ReadLens<ArrayPayment, int[]> TO_VALUE_READ_LENS = Lenses.readLens(ArrayPayment::getTo).andThen(Lenses.readLens(Box<int[]>::getValue));

    public static final ReadWriteLens<ArrayPayment, int[]> TO_VALUE_READ_WRITE_LENS = Lenses.readLens(ArrayPayment::getTo).andThen(Lenses.readWriteLens(Box<int[]>::getValue, Box<int[]>::setValue));

    private ArrayPaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

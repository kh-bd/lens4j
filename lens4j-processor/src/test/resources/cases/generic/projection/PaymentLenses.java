package cases.generic.projection;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, StrFrom> FROM_READ_LENS = Lenses.readLens(Payment::getFrom);

    public static final ReadWriteLens<Payment, StrFrom> FROM_READ_WRITE_LENS = Lenses.readWriteLens(Payment::getFrom, Payment::setFrom);

    public static final ReadLens<Payment, String> FROM_VALUE_READ_LENS = Lenses.readLens(Payment::getFrom).andThen(Lenses.readLens(StrFrom::getValue));

    public static final ReadWriteLens<Payment, String> FROM_VALUE_READ_WRITE_LENS = Lenses.readLens(Payment::getFrom).andThen(Lenses.readWriteLens(StrFrom::getValue, StrFrom::setValue));

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

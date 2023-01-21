package cases.method.generic.class_generics;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import java.util.List;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, String> FROM_READ_LENS = Lenses.readLens(Payment::from);

    public static final ReadLens<Payment, List<String>> FROMS_READ_LENS = Lenses.readLens(Payment::froms);

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

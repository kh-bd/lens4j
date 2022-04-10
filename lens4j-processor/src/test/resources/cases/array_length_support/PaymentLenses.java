package cases.array_length_support;

import common.Payer;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Integer;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Integer> ARRAY_LENGTH_READ_LENS = Lenses.readLens(Payment::getPayers).andThen(Lenses.readLens((Payer[] o) -> o.length));

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

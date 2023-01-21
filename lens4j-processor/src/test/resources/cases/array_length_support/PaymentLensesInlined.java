package cases.array_length_support;

import common.Payer;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Integer> ARRAY_LENGTH_READ_LENS = new ReadLens<Payment, Integer>() {
        @Override
        public final Integer get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer[] payers = object.getPayers();
            if (payers == null) {
                return null;
            }
            return payers.length;
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

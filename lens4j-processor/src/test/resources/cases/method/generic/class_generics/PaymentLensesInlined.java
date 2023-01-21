package cases.method.generic.class_generics;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import java.util.List;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, String> FROM_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.from();
        }
    };

    public static final ReadLens<Payment, List<String>> FROMS_READ_LENS = new ReadLens<Payment, List<String>>() {
        @Override
        public final List<String> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.froms();
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

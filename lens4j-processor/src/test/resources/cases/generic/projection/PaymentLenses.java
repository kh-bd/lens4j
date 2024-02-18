package cases.generic.projection;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, StrFrom> FROM_READ_LENS = new ReadLens<Payment, StrFrom>() {
        @Override
        public final StrFrom get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }
    };

    public static final ReadWriteLens<Payment, StrFrom> FROM_READ_WRITE_LENS = new ReadWriteLens<Payment, StrFrom>() {
        @Override
        public final StrFrom get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }

        @Override
        public final void set(Payment object, StrFrom property) {
            if (object == null) {
                return;
            }
            object.setFrom(property);
        }
    };

    public static final ReadLens<Payment, String> FROM_VALUE_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            StrFrom from = object.getFrom();
            if (from == null) {
                return null;
            }
            return from.getValue();
        }
    };

    public static final ReadWriteLens<Payment, String> FROM_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            StrFrom from = object.getFrom();
            if (from == null) {
                return null;
            }
            return from.getValue();
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            StrFrom from = object.getFrom();
            if (from == null) {
                return;
            }
            from.setValue(property);
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

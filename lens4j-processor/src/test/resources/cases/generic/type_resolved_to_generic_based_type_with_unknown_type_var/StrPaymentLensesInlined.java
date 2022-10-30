package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class StrPaymentLenses {
    public static final ReadLens<StrPayment, String> FROM_READ_LENS = new ReadLens<StrPayment, String>() {
        @Override
        public final String get(StrPayment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }
    };

    public static final ReadWriteLens<StrPayment, String> FROM_READ_WRITE_LENS = new ReadWriteLens<StrPayment, String>() {
        @Override
        public final String get(StrPayment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }

        @Override
        public final void set(StrPayment object, String property) {
            if (object == null) {
                return;
            }
            object.setFrom(property);
        }
    };

    public static final ReadLens<StrPayment, Box<String>> TO_READ_LENS = new ReadLens<StrPayment, Box<String>>() {
        @Override
        public final Box<String> get(StrPayment object) {
            if (object == null) {
                return null;
            }
            return object.getTo();
        }
    };

    public static final ReadWriteLens<StrPayment, Box<String>> TO_READ_WRITE_LENS = new ReadWriteLens<StrPayment, Box<String>>() {
        @Override
        public final Box<String> get(StrPayment object) {
            if (object == null) {
                return null;
            }
            return object.getTo();
        }

        @Override
        public final void set(StrPayment object, Box<String> property) {
            if (object == null) {
                return;
            }
            object.setTo(property);
        }
    };

    public static final ReadLens<StrPayment, String> TO_VALUE_READ_LENS = new ReadLens<StrPayment, String>() {
        @Override
        public final String get(StrPayment object) {
            if (object == null) {
                return null;
            }
            Box<String> to = object.getTo();
            if (to == null) {
                return null;
            }
            return to.getValue();
        }
    };

    public static final ReadWriteLens<StrPayment, String> TO_VALUE_READ_WRITE_LENS = new ReadWriteLens<StrPayment, String>() {
        @Override
        public final String get(StrPayment object) {
            if (object == null) {
                return null;
            }
            Box<String> to = object.getTo();
            if (to == null) {
                return null;
            }
            return to.getValue();
        }

        @Override
        public final void set(StrPayment object, String property) {
            if (object == null) {
                return;
            }
            Box<String> to = object.getTo();
            if (to == null) {
                return;
            }
            to.setValue(property);
        }
    };

    private StrPaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

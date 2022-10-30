package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class GenPaymentLenses {
    public static final ReadLens<GenPayment, Box<String>> FROM_READ_LENS = new ReadLens<GenPayment, Box<String>>() {
        @Override
        public final Box<String> get(GenPayment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }
    };

    public static final ReadWriteLens<GenPayment, Box<String>> FROM_READ_WRITE_LENS = new ReadWriteLens<GenPayment, Box<String>>() {
        @Override
        public final Box<String> get(GenPayment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }

        @Override
        public final void set(GenPayment object, Box<String> property) {
            if (object == null) {
                return;
            }
            object.setFrom(property);
        }
    };

    public static final ReadLens<GenPayment, Box<Box<String>>> TO_READ_LENS = new ReadLens<GenPayment, Box<Box<String>>>() {
        @Override
        public final Box<Box<String>> get(GenPayment object) {
            if (object == null) {
                return null;
            }
            return object.getTo();
        }
    };

    public static final ReadWriteLens<GenPayment, Box<Box<String>>> TO_READ_WRITE_LENS = new ReadWriteLens<GenPayment, Box<Box<String>>>() {
        @Override
        public final Box<Box<String>> get(GenPayment object) {
            if (object == null) {
                return null;
            }
            return object.getTo();
        }

        @Override
        public final void set(GenPayment object, Box<Box<String>> property) {
            if (object == null) {
                return;
            }
            object.setTo(property);
        }
    };

    public static final ReadLens<GenPayment, Box<String>> TO_VALUE_READ_LENS = new ReadLens<GenPayment, Box<String>>() {
        @Override
        public final Box<String> get(GenPayment object) {
            if (object == null) {
                return null;
            }
            Box<Box<String>> to = object.getTo();
            if (to == null) {
                return null;
            }
            return to.getValue();
        }
    };

    public static final ReadWriteLens<GenPayment, Box<String>> TO_VALUE_READ_WRITE_LENS = new ReadWriteLens<GenPayment, Box<String>>() {
        @Override
        public final Box<String> get(GenPayment object) {
            if (object == null) {
                return null;
            }
            Box<Box<String>> to = object.getTo();
            if (to == null) {
                return null;
            }
            return to.getValue();
        }

        @Override
        public final void set(GenPayment object, Box<String> property) {
            if (object == null) {
                return;
            }
            Box<Box<String>> to = object.getTo();
            if (to == null) {
                return;
            }
            to.setValue(property);
        }
    };

    public static final ReadLens<GenPayment, String> TO_VALUE_VALUE_READ_LENS = new ReadLens<GenPayment, String>() {
        @Override
        public final String get(GenPayment object) {
            if (object == null) {
                return null;
            }
            Box<Box<String>> to = object.getTo();
            if (to == null) {
                return null;
            }
            Box<String> value = to.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }
    };

    public static final ReadWriteLens<GenPayment, String> TO_VALUE_VALUE_READ_WRITE_LENS = new ReadWriteLens<GenPayment, String>() {
        @Override
        public final String get(GenPayment object) {
            if (object == null) {
                return null;
            }
            Box<Box<String>> to = object.getTo();
            if (to == null) {
                return null;
            }
            Box<String> value = to.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }

        @Override
        public final void set(GenPayment object, String property) {
            if (object == null) {
                return;
            }
            Box<Box<String>> to = object.getTo();
            if (to == null) {
                return;
            }
            Box<String> value = to.getValue();
            if (value == null) {
                return;
            }
            value.setValue(property);
        }
    };

    private GenPaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

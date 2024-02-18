package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class ArrayPaymentLenses {
    public static final ReadLens<ArrayPayment, int[]> FROM_READ_LENS = new ReadLens<ArrayPayment, int[]>() {
        @Override
        public final int[] get(ArrayPayment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }
    };

    public static final ReadWriteLens<ArrayPayment, int[]> FROM_READ_WRITE_LENS = new ReadWriteLens<ArrayPayment, int[]>() {
        @Override
        public final int[] get(ArrayPayment object) {
            if (object == null) {
                return null;
            }
            return object.getFrom();
        }

        @Override
        public final void set(ArrayPayment object, int[] property) {
            if (object == null) {
                return;
            }
            object.setFrom(property);
        }
    };

    public static final ReadLens<ArrayPayment, Box<int[]>> TO_READ_LENS = new ReadLens<ArrayPayment, Box<int[]>>() {
        @Override
        public final Box<int[]> get(ArrayPayment object) {
            if (object == null) {
                return null;
            }
            return object.getTo();
        }
    };

    public static final ReadWriteLens<ArrayPayment, Box<int[]>> TO_READ_WRITE_LENS = new ReadWriteLens<ArrayPayment, Box<int[]>>() {
        @Override
        public final Box<int[]> get(ArrayPayment object) {
            if (object == null) {
                return null;
            }
            return object.getTo();
        }

        @Override
        public final void set(ArrayPayment object, Box<int[]> property) {
            if (object == null) {
                return;
            }
            object.setTo(property);
        }
    };

    public static final ReadLens<ArrayPayment, int[]> TO_VALUE_READ_LENS = new ReadLens<ArrayPayment, int[]>() {
        @Override
        public final int[] get(ArrayPayment object) {
            if (object == null) {
                return null;
            }
            Box<int[]> to = object.getTo();
            if (to == null) {
                return null;
            }
            return to.getValue();
        }
    };

    public static final ReadWriteLens<ArrayPayment, int[]> TO_VALUE_READ_WRITE_LENS = new ReadWriteLens<ArrayPayment, int[]>() {
        @Override
        public final int[] get(ArrayPayment object) {
            if (object == null) {
                return null;
            }
            Box<int[]> to = object.getTo();
            if (to == null) {
                return null;
            }
            return to.getValue();
        }

        @Override
        public final void set(ArrayPayment object, int[] property) {
            if (object == null) {
                return;
            }
            Box<int[]> to = object.getTo();
            if (to == null) {
                return;
            }
            to.setValue(property);
        }
    };

    private ArrayPaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

package cases.generic.field_type_declared_with_unknown_type_param;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Box<Currency>> BOXED_READ_LENS = new ReadLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxed();
        }
    };

    public static final ReadWriteLens<Payment, Box<Currency>> BOXED_READ_WRITE_LENS = new ReadWriteLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxed();
        }

        @Override
        public final void set(Payment object, Box<Currency> property) {
            if (object == null) {
                return;
            }
            object.setBoxed(property);
        }
    };

    public static final ReadLens<Payment, Currency> BOXED_VALUE_READ_LENS = new ReadLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxed = object.getBoxed();
            if (boxed == null) {
                return null;
            }
            return boxed.getValue();
        }
    };

    public static final ReadWriteLens<Payment, Currency> BOXED_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxed = object.getBoxed();
            if (boxed == null) {
                return null;
            }
            return boxed.getValue();
        }

        @Override
        public final void set(Payment object, Currency property) {
            if (object == null) {
                return;
            }
            Box<Currency> boxed = object.getBoxed();
            if (boxed == null) {
                return;
            }
            boxed.setValue(property);
        }
    };

    public static final ReadLens<Payment, String> BOXED_VALUE_CODE_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxed = object.getBoxed();
            if (boxed == null) {
                return null;
            }
            Currency value = boxed.getValue();
            if (value == null) {
                return null;
            }
            return value.getCode();
        }
    };

    public static final ReadWriteLens<Payment, String> BOXED_VALUE_CODE_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxed = object.getBoxed();
            if (boxed == null) {
                return null;
            }
            Currency value = boxed.getValue();
            if (value == null) {
                return null;
            }
            return value.getCode();
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Box<Currency> boxed = object.getBoxed();
            if (boxed == null) {
                return;
            }
            Currency value = boxed.getValue();
            if (value == null) {
                return;
            }
            value.setCode(property);
        }
    };

    public static final ReadLens<Payment, Box<Box<Currency>>> BOXED_BOXED_READ_LENS = new ReadLens<Payment, Box<Box<Currency>>>() {
        @Override
        public final Box<Box<Currency>> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxedBoxed();
        }
    };

    public static final ReadWriteLens<Payment, Box<Box<Currency>>> BOXED_BOXED_READ_WRITE_LENS = new ReadWriteLens<Payment, Box<Box<Currency>>>() {
        @Override
        public final Box<Box<Currency>> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxedBoxed();
        }

        @Override
        public final void set(Payment object, Box<Box<Currency>> property) {
            if (object == null) {
                return;
            }
            object.setBoxedBoxed(property);
        }
    };

    public static final ReadLens<Payment, Box<Currency>> BOXED_BOXED_VALUE_READ_LENS = new ReadLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return null;
            }
            return boxedBoxed.getValue();
        }
    };

    public static final ReadWriteLens<Payment, Box<Currency>> BOXED_BOXED_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return null;
            }
            return boxedBoxed.getValue();
        }

        @Override
        public final void set(Payment object, Box<Currency> property) {
            if (object == null) {
                return;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return;
            }
            boxedBoxed.setValue(property);
        }
    };

    public static final ReadLens<Payment, Currency> BOXED_BOXED_VALUE_VALUE_READ_LENS = new ReadLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return null;
            }
            Box<Currency> value = boxedBoxed.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }
    };

    public static final ReadWriteLens<Payment, Currency> BOXED_BOXED_VALUE_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return null;
            }
            Box<Currency> value = boxedBoxed.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }

        @Override
        public final void set(Payment object, Currency property) {
            if (object == null) {
                return;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return;
            }
            Box<Currency> value = boxedBoxed.getValue();
            if (value == null) {
                return;
            }
            value.setValue(property);
        }
    };

    public static final ReadLens<Payment, String> BOXED_BOXED_VALUE_VALUE_CODE_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return null;
            }
            Box<Currency> value = boxedBoxed.getValue();
            if (value == null) {
                return null;
            }
            Currency value1 = value.getValue();
            if (value1 == null) {
                return null;
            }
            return value1.getCode();
        }
    };

    public static final ReadWriteLens<Payment, String> BOXED_BOXED_VALUE_VALUE_CODE_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return null;
            }
            Box<Currency> value = boxedBoxed.getValue();
            if (value == null) {
                return null;
            }
            Currency value1 = value.getValue();
            if (value1 == null) {
                return null;
            }
            return value1.getCode();
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Box<Box<Currency>> boxedBoxed = object.getBoxedBoxed();
            if (boxedBoxed == null) {
                return;
            }
            Box<Currency> value = boxedBoxed.getValue();
            if (value == null) {
                return;
            }
            Currency value1 = value.getValue();
            if (value1 == null) {
                return;
            }
            value1.setCode(property);
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

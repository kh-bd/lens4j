package cases.generic.field_type_declared_with_known_type_param;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Box<Currency>> BOXED_CURRENCY_READ_LENS = new ReadLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxedCurrency();
        }
    };

    public static final ReadWriteLens<Payment, Box<Currency>> BOXED_CURRENCY_READ_WRITE_LENS = new ReadWriteLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxedCurrency();
        }

        @Override
        public final void set(Payment object, Box<Currency> property) {
            if (object == null) {
                return;
            }
            object.setBoxedCurrency(property);
        }
    };

    public static final ReadLens<Payment, Currency> BOXED_CURRENCY_VALUE_READ_LENS = new ReadLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxedCurrency = object.getBoxedCurrency();
            if (boxedCurrency == null) {
                return null;
            }
            return boxedCurrency.getValue();
        }
    };

    public static final ReadWriteLens<Payment, Currency> BOXED_CURRENCY_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxedCurrency = object.getBoxedCurrency();
            if (boxedCurrency == null) {
                return null;
            }
            return boxedCurrency.getValue();
        }

        @Override
        public final void set(Payment object, Currency property) {
            if (object == null) {
                return;
            }
            Box<Currency> boxedCurrency = object.getBoxedCurrency();
            if (boxedCurrency == null) {
                return;
            }
            boxedCurrency.setValue(property);
        }
    };

    public static final ReadLens<Payment, String> BOXED_CURRENCY_VALUE_CODE_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxedCurrency = object.getBoxedCurrency();
            if (boxedCurrency == null) {
                return null;
            }
            Currency value = boxedCurrency.getValue();
            if (value == null) {
                return null;
            }
            return value.getCode();
        }
    };

    public static final ReadWriteLens<Payment, String> BOXED_CURRENCY_VALUE_CODE_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Currency> boxedCurrency = object.getBoxedCurrency();
            if (boxedCurrency == null) {
                return null;
            }
            Currency value = boxedCurrency.getValue();
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
            Box<Currency> boxedCurrency = object.getBoxedCurrency();
            if (boxedCurrency == null) {
                return;
            }
            Currency value = boxedCurrency.getValue();
            if (value == null) {
                return;
            }
            value.setCode(property);
        }
    };

    public static final ReadLens<Payment, Box<Box<Currency>>> BOXED_BOXED_CURRENCY_READ_LENS = new ReadLens<Payment, Box<Box<Currency>>>() {
        @Override
        public final Box<Box<Currency>> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxedBoxedCurrency();
        }
    };

    public static final ReadWriteLens<Payment, Box<Box<Currency>>> BOXED_BOXED_CURRENCY_READ_WRITE_LENS = new ReadWriteLens<Payment, Box<Box<Currency>>>() {
        @Override
        public final Box<Box<Currency>> get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getBoxedBoxedCurrency();
        }

        @Override
        public final void set(Payment object, Box<Box<Currency>> property) {
            if (object == null) {
                return;
            }
            object.setBoxedBoxedCurrency(property);
        }
    };

    public static final ReadLens<Payment, Box<Currency>> BOXED_BOXED_CURRENCY_VALUE_READ_LENS = new ReadLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return null;
            }
            return boxedBoxedCurrency.getValue();
        }
    };

    public static final ReadWriteLens<Payment, Box<Currency>> BOXED_BOXED_CURRENCY_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, Box<Currency>>() {
        @Override
        public final Box<Currency> get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return null;
            }
            return boxedBoxedCurrency.getValue();
        }

        @Override
        public final void set(Payment object, Box<Currency> property) {
            if (object == null) {
                return;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return;
            }
            boxedBoxedCurrency.setValue(property);
        }
    };

    public static final ReadLens<Payment, Currency> BOXED_BOXED_CURRENCY_VALUE_VALUE_READ_LENS = new ReadLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return null;
            }
            Box<Currency> value = boxedBoxedCurrency.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }
    };

    public static final ReadWriteLens<Payment, Currency> BOXED_BOXED_CURRENCY_VALUE_VALUE_READ_WRITE_LENS = new ReadWriteLens<Payment, Currency>() {
        @Override
        public final Currency get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return null;
            }
            Box<Currency> value = boxedBoxedCurrency.getValue();
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
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return;
            }
            Box<Currency> value = boxedBoxedCurrency.getValue();
            if (value == null) {
                return;
            }
            value.setValue(property);
        }
    };

    public static final ReadLens<Payment, String> BOXED_BOXED_CURRENCY_VALUE_VALUE_CODE_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return null;
            }
            Box<Currency> value = boxedBoxedCurrency.getValue();
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

    public static final ReadWriteLens<Payment, String> BOXED_BOXED_CURRENCY_VALUE_VALUE_CODE_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return null;
            }
            Box<Currency> value = boxedBoxedCurrency.getValue();
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
            Box<Box<Currency>> boxedBoxedCurrency = object.getBoxedBoxedCurrency();
            if (boxedBoxedCurrency == null) {
                return;
            }
            Box<Currency> value = boxedBoxedCurrency.getValue();
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

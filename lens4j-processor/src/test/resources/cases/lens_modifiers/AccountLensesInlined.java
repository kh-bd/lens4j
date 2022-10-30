package cases.lens_modifiers;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {
    public static final ReadLens<Account, String> READ_DEFAULT = new ReadLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }
    };

    public static final ReadLens<Account, String> READ_PUBLIC = new ReadLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }
    };

    static final ReadLens<Account, String> READ_PACKAGE = new ReadLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }
    };

    public static final ReadWriteLens<Account, String> WRITE_DEFAULT = new ReadWriteLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }

        @Override
        public final void set(Account object, String property) {
            if (object == null) {
                return;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return;
            }
            currency.setCode(property);
        }
    };

    public static final ReadWriteLens<Account, String> WRITE_PUBLIC = new ReadWriteLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }

        @Override
        public final void set(Account object, String property) {
            if (object == null) {
                return;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return;
            }
            currency.setCode(property);
        }
    };

    static final ReadWriteLens<Account, String> WRITE_PACKAGE = new ReadWriteLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }

        @Override
        public final void set(Account object, String property) {
            if (object == null) {
                return;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return;
            }
            currency.setCode(property);
        }
    };

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

package cases.single_property;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {
    public static final ReadLens<Account, String> ACCOUNT_NUMBER_READ_LENS = new ReadLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            return object.getAccountNumber();
        }
    };

    public static final ReadWriteLens<Account, String> ACCOUNT_NUMBER_READ_WRITE_LENS = new ReadWriteLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            return object.getAccountNumber();
        }

        @Override
        public final void set(Account object, String property) {
            if (object == null) {
                return;
            }
            object.setAccountNumber(property);
        }
    };

    public static final ReadLens<Account, String> ACCOUNT_CURRENCY_CODE_READ_LENS = new ReadLens<Account, String>() {
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

    public static final ReadWriteLens<Account, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = new ReadWriteLens<Account, Integer>() {
        @Override
        public final Integer get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getId();
        }

        @Override
        public final void set(Account object, Integer property) {
            if (object == null) {
                return;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return;
            }
            currency.setId(property);
        }
    };

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

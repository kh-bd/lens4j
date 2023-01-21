package cases.record.field;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {
    public static final ReadLens<Account, Currency> CUR_LENS = new ReadLens<Account, Currency>() {
        @Override
        public final Currency get(Account object) {
            if (object == null) {
                return null;
            }
            return object.currency();
        }

    }

    public static final ReadWriteLens<Account, String> CUR_CODE_LENS = new ReadWriteLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.currency();
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
            Currency currency = object.currency();
            if (currency == null) {
                return;
            }
            currency.setCode(property);
        }
    }

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

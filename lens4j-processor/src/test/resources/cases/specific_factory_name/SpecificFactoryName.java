package cases.specific_factory_name;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class SpecificFactoryName {
    public static final ReadLens<AccountWithSpecificFactoryName, String> ACCOUNT_CURRENCY_CODE_READ_LENS = new ReadLens<AccountWithSpecificFactoryName, String>() {
        @Override
        public final String get(AccountWithSpecificFactoryName object) {
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

    public static final ReadWriteLens<AccountWithSpecificFactoryName, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = new ReadWriteLens<AccountWithSpecificFactoryName, Integer>() {
        @Override
        public final Integer get(AccountWithSpecificFactoryName object) {
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
        public final void set(AccountWithSpecificFactoryName object, Integer property) {
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

    private SpecificFactoryName() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

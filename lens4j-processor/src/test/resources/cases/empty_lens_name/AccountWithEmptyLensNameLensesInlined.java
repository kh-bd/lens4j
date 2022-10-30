package cases.empty_lens_name;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountWithEmptyLensNameLenses {
    public static final ReadLens<AccountWithEmptyLensName, String> CURRENCY_FIELD_FOR_TEST_CODE_READ_LENS = new ReadLens<AccountWithEmptyLensName, String>() {
        @Override
        public final String get(AccountWithEmptyLensName object) {
            if (object == null) {
                return null;
            }
            Currency currencyFieldForTest = object.getCurrencyFieldForTest();
            if (currencyFieldForTest == null) {
                return null;
            }
            return currencyFieldForTest.getCode();
        }
    };

    public static final ReadWriteLens<AccountWithEmptyLensName, Integer> CURRENCY_FIELD_FOR_TEST_ID_READ_WRITE_LENS = new ReadWriteLens<AccountWithEmptyLensName, Integer>() {
        @Override
        public final Integer get(AccountWithEmptyLensName object) {
            if (object == null) {
                return null;
            }
            Currency currencyFieldForTest = object.getCurrencyFieldForTest();
            if (currencyFieldForTest == null) {
                return null;
            }
            return currencyFieldForTest.getId();
        }

        @Override
        public final void set(AccountWithEmptyLensName object, Integer property) {
            if (object == null) {
                return;
            }
            Currency currencyFieldForTest = object.getCurrencyFieldForTest();
            if (currencyFieldForTest == null) {
                return;
            }
            currencyFieldForTest.setId(property);
        }
    };

    private AccountWithEmptyLensNameLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}

package cases.package_private_class;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class PackagePrivateAccountLenses {
    public static final ReadLens<PackagePrivateAccount, String> ACCOUNT_NUMBER_READ_LENS = new ReadLens<PackagePrivateAccount, String>() {
        @Override
        public final String get(PackagePrivateAccount object) {
            if (object == null) {
                return null;
            }
            return object.getAccountNumber();
        }
    };

    public static final ReadWriteLens<PackagePrivateAccount, String> ACCOUNT_NUMBER_READ_WRITE_LENS = new ReadWriteLens<PackagePrivateAccount, String>() {
        @Override
        public final String get(PackagePrivateAccount object) {
            if (object == null) {
                return null;
            }
            return object.getAccountNumber();
        }

        @Override
        public final void set(PackagePrivateAccount object, String property) {
            if (object == null) {
                return;
            }
            object.setAccountNumber(property);
        }
    };

    public static final ReadLens<PackagePrivateAccount, String> ACCOUNT_CURRENCY_CODE_READ_LENS = new ReadLens<PackagePrivateAccount, String>() {
        @Override
        public final String get(PackagePrivateAccount object) {
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

    public static final ReadWriteLens<PackagePrivateAccount, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = new ReadWriteLens<PackagePrivateAccount, Integer>() {
        @Override
        public final Integer get(PackagePrivateAccount object) {
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
        public final void set(PackagePrivateAccount object, Integer property) {
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

    private PackagePrivateAccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
